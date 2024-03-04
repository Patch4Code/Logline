package com.moritz.movieappuitest.views.list.details

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.moritz.movieappuitest.Screen
import com.moritz.movieappuitest.dataclasses.Movie
import com.moritz.movieappuitest.dataclasses.MovieList
import com.moritz.movieappuitest.utils.JSONHelper
import com.moritz.movieappuitest.utils.JSONHelper.toJson
import com.moritz.movieappuitest.viewmodels.ListViewModel
import com.moritz.movieappuitest.viewmodels.NavigationViewModel
import com.moritz.movieappuitest.views.list.details.dialogs.AddMovieToListDialog
import com.moritz.movieappuitest.views.list.details.dialogs.DeleteMovieFromListDialog
import com.moritz.movieappuitest.views.list.details.dialogs.EditListDialog
import com.moritz.movieappuitest.views.list.details.dialogs.ListSettingsBottomSheet
import com.moritz.movieappuitest.views.list.details.pagecontent.ListContent
import com.moritz.movieappuitest.views.list.overview.dialogs.DeleteListDialog
import java.net.URLDecoder
import java.net.URLEncoder

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListView(navController: NavController, navViewModel: NavigationViewModel, movieListString: String?, listViewModel: ListViewModel = viewModel()){

    val decodedMovieListString = URLDecoder.decode(movieListString, "UTF-8")
    val movieListData: MovieList = JSONHelper.fromJson(decodedMovieListString)

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.ListScreen)
        listViewModel.setList(movieListData)
    }
    val movieList = listViewModel.movieList.observeAsState().value

    val openAddMovieDialog = remember { mutableStateOf(false)  }
    val openDeleteMovieDialog = remember { mutableStateOf(false)  }
    val movieToDelete = remember { mutableStateOf<Movie?>(null) }
    val openEditListDialog = remember { mutableStateOf(false)  }
    val openDeleteListDialog = remember { mutableStateOf(false)  }
    val showBottomSheet = remember { mutableStateOf(false)  }

    val context = LocalContext.current

    Scaffold (
        floatingActionButton = {
            FloatingActionButton(onClick = { openAddMovieDialog.value = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add new movie to list")
            }
        }
    ){
        ListContent(movieList, showBottomSheet, openDeleteMovieDialog, movieToDelete, navController)

        DeleteMovieFromListDialog(openDeleteMovieDialog = openDeleteMovieDialog.value,
            onDelete = {
                movieToDelete.value?.id?.let { movieToDelete->
                    listViewModel.removeMovieFromList(movieToDelete)
                }
                movieToDelete.value = null
                openDeleteMovieDialog.value = false
            },
            onCancel = {
                openDeleteMovieDialog.value = false

                //workaround with scene reload
                val jsonMovieList = movieList?.toJson()
                val encodedJsonMovieList = URLEncoder.encode(jsonMovieList, "UTF-8")
                navController.navigate(Screen.ListScreen.withArgs(encodedJsonMovieList)){
                    popUpTo(Screen.ListsTableScreen.route){
                        inclusive = false
                    }
                }
            }
        )

        AddMovieToListDialog(
            openAddMovieDialog = openAddMovieDialog.value,
            listViewModel = listViewModel,
            closeDialog = {
                openAddMovieDialog.value = false
            }
        )

        ListSettingsBottomSheet(
            showBottomSheet = showBottomSheet.value,
            onClose = {showBottomSheet.value = false},
            onEdit = {
                showBottomSheet.value = false
                openEditListDialog.value = true
            },
            onDelete = {
                showBottomSheet.value = false
                openDeleteListDialog.value = true
            }
        )

        EditListDialog(
            initialMovieTitle = movieList?.name ?: "",
            initialIsPublic = movieList?.isPublic ?: true,
            openEditListDialog = openEditListDialog.value,
            onSave = {newName, isPublic->
                if(listViewModel.isListNameUnique(newName)){
                    openEditListDialog.value = false
                    listViewModel.editList(newName, isPublic)
                }else{
                    Toast.makeText(context, "List name already exists!", Toast.LENGTH_LONG).show()
                }


            },
            onCancel = {openEditListDialog.value = false}
        )

        DeleteListDialog(
            openDeleteListDialog = openDeleteListDialog.value,
            onDelete = {
                listViewModel.deleteList()
                openDeleteListDialog.value = false
                navController.navigate(Screen.ListsTableScreen.route){
                    popUpTo(Screen.ListsTableScreen.route){
                        inclusive = true
                    }
                }
            },
            onCancel = { openDeleteListDialog.value = false }
        )
    }
}