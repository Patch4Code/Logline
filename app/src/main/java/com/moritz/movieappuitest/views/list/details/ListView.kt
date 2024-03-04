package com.moritz.movieappuitest.views.list.details

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.moritz.movieappuitest.Screen
import com.moritz.movieappuitest.dataclasses.Movie
import com.moritz.movieappuitest.dataclasses.MovieList
import com.moritz.movieappuitest.utils.JSONHelper
import com.moritz.movieappuitest.utils.JSONHelper.toJson
import com.moritz.movieappuitest.viewmodels.ListViewModel
import com.moritz.movieappuitest.viewmodels.NavigationViewModel
import com.moritz.movieappuitest.views.list.overview.dialogs.DeleteListDialog
import com.moritz.movieappuitest.views.swipe.swipeToDeleteContainer
import java.net.URLDecoder
import java.net.URLEncoder

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
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
    val showBottomSheet = remember { mutableStateOf(false)  }
    val openEditListDialog = remember { mutableStateOf(false)  }
    val openDeleteListDialog = remember { mutableStateOf(false)  }

    val context = LocalContext.current

    Scaffold (
        floatingActionButton = {
            FloatingActionButton(onClick = { openAddMovieDialog.value = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add new movie to list")
            }
        }
    ){
        Column {
            Row (
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(text = movieList?.name ?: "N/A", modifier = Modifier.weight(1f), style = MaterialTheme.typography.titleLarge)
                Icon(
                    imageVector = if(movieList?.isPublic == true) Icons.Default.Public else Icons.Default.Lock,
                    contentDescription = if(movieList?.isPublic == true) "Public List" else "Private List",
                )
                IconButton(onClick = { showBottomSheet.value = true }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Open List Settings")
                }
            }

            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)){
                itemsIndexed(
                    items = movieList?.movies ?: emptyList(),
                    key = { _, item -> item.hashCode() }
                ) { _, movie ->
                    swipeToDeleteContainer(
                        item = movie,
                        onDelete = {
                            movieToDelete.value = movie
                            openDeleteMovieDialog.value = true
                        }
                    ) {
                        ListItem(navController, movie)
                    }
                }
            }
        }
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