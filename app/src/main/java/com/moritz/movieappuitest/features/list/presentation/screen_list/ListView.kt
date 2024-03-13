package com.moritz.movieappuitest.features.list.presentation.screen_list

import android.annotation.SuppressLint
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
import com.moritz.movieappuitest.features.core.domain.model.Movie
import com.moritz.movieappuitest.features.core.presentation.utils.JSONHelper
import com.moritz.movieappuitest.features.list.domain.model.MovieList
import com.moritz.movieappuitest.features.list.presentation.components.list.ListContent
import com.moritz.movieappuitest.features.list.presentation.components.list.dialogs.AddMovieToListDialog
import com.moritz.movieappuitest.features.list.presentation.components.list.dialogs.DeleteMovieFromListDialog
import com.moritz.movieappuitest.features.list.presentation.components.list.dialogs.EditListDialog
import com.moritz.movieappuitest.features.list.presentation.components.list.dialogs.ListSettingsBottomSheet
import com.moritz.movieappuitest.features.list.presentation.components.lists_table.dialogs.DeleteListDialog
import com.moritz.movieappuitest.features.list.presentation.utils.ListDialogsExtensions.onCancelDeleteMovieFromList
import com.moritz.movieappuitest.features.list.presentation.utils.ListDialogsExtensions.onDeleteList
import com.moritz.movieappuitest.features.list.presentation.utils.ListDialogsExtensions.onDeleteListBottomSheet
import com.moritz.movieappuitest.features.list.presentation.utils.ListDialogsExtensions.onDeleteMovieFromList
import com.moritz.movieappuitest.features.list.presentation.utils.ListDialogsExtensions.onEditListBottomSheet
import com.moritz.movieappuitest.features.list.presentation.utils.ListDialogsExtensions.onSaveEditList
import com.moritz.movieappuitest.features.navigation.domain.model.Screen
import com.moritz.movieappuitest.features.navigation.presentation.screen_navigation.NavigationViewModel
import java.net.URLDecoder

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


        //Dialogs and BottomSheet

        DeleteMovieFromListDialog(openDeleteMovieDialog = openDeleteMovieDialog.value,
            onDelete = { listViewModel.onDeleteMovieFromList(movieToDelete, openDeleteMovieDialog) },
            onCancel = { onCancelDeleteMovieFromList(openDeleteMovieDialog, movieList, navController) })

        AddMovieToListDialog(openAddMovieDialog = openAddMovieDialog.value,
            listViewModel = listViewModel,
            closeDialog = { openAddMovieDialog.value = false })

        ListSettingsBottomSheet(showBottomSheet = showBottomSheet.value,
            onClose = {showBottomSheet.value = false},
            onEdit = { onEditListBottomSheet(showBottomSheet, openEditListDialog) },
            onDelete = { onDeleteListBottomSheet(showBottomSheet, openDeleteListDialog) })

        EditListDialog(
            initialMovieTitle = movieList?.name ?: "",
            initialIsPublic = movieList?.isPublic ?: true,
            openEditListDialog = openEditListDialog.value,
            onSave = {newName, isPublic-> listViewModel.onSaveEditList(newName, isPublic, openEditListDialog, context) },
            onCancel = {openEditListDialog.value = false})

        DeleteListDialog(
            openDeleteListDialog = openDeleteListDialog.value,
            onDelete = { listViewModel.onDeleteList(openDeleteListDialog, navController) },
            onCancel = { openDeleteListDialog.value = false })
    }
}