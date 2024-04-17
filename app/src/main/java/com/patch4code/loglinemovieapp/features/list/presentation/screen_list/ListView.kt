package com.patch4code.loglinemovieapp.features.list.presentation.screen_list

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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.presentation.utils.JSONHelper
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieList
import com.patch4code.loglinemovieapp.features.list.presentation.components.list.ListContent
import com.patch4code.loglinemovieapp.features.list.presentation.components.list.dialogs.AddMovieToListDialog
import com.patch4code.loglinemovieapp.features.list.presentation.components.list.dialogs.DeleteMovieFromListDialog
import com.patch4code.loglinemovieapp.features.list.presentation.components.list.dialogs.EditListDialog
import com.patch4code.loglinemovieapp.features.list.presentation.components.list.dialogs.ListSettingsBottomSheet
import com.patch4code.loglinemovieapp.features.list.presentation.components.lists_table.dialogs.DeleteListDialog
import com.patch4code.loglinemovieapp.features.list.presentation.utils.ListDialogsExtensions.onCancelDeleteMovieFromList
import com.patch4code.loglinemovieapp.features.list.presentation.utils.ListDialogsExtensions.onDeleteList
import com.patch4code.loglinemovieapp.features.list.presentation.utils.ListDialogsExtensions.onDeleteListBottomSheet
import com.patch4code.loglinemovieapp.features.list.presentation.utils.ListDialogsExtensions.onDeleteMovieFromList
import com.patch4code.loglinemovieapp.features.list.presentation.utils.ListDialogsExtensions.onEditListBottomSheet
import com.patch4code.loglinemovieapp.features.list.presentation.utils.ListDialogsExtensions.onSaveEditList
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel
import com.patch4code.loglinemovieapp.room_database.LoglineDatabase
import java.net.URLDecoder

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListView(
    navController: NavController,
    navViewModel: NavigationViewModel,
    movieListString: String?,
    db: LoglineDatabase,
    listViewModel: ListViewModel = viewModel(
        factory = ListViewModelFactory(db.movieListDao)
    )
){

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

    Scaffold (
        floatingActionButton = {
            FloatingActionButton(onClick = { openAddMovieDialog.value = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add new movie to list")
            }
        }
    ){
        ListContent(movieList, showBottomSheet, openDeleteMovieDialog, movieToDelete, navController, listViewModel)


        //Dialogs and BottomSheet

        DeleteMovieFromListDialog(openDeleteMovieDialog = openDeleteMovieDialog.value,
            onDelete = { listViewModel.onDeleteMovieFromList(movieToDelete, openDeleteMovieDialog, movieList, navController) },
            onCancel = { onCancelDeleteMovieFromList(openDeleteMovieDialog, movieList, navController) })

        AddMovieToListDialog(openAddMovieDialog = openAddMovieDialog, listViewModel = listViewModel)

        ListSettingsBottomSheet(showBottomSheet = showBottomSheet.value,
            onClose = {showBottomSheet.value = false},
            onEdit = { onEditListBottomSheet(showBottomSheet, openEditListDialog) },
            onDelete = { onDeleteListBottomSheet(showBottomSheet, openDeleteListDialog) })

        EditListDialog(
            initialMovieTitle = movieList?.name ?: "",
            initialIsRankedState = movieList?.isRanked ?: false,
            openEditListDialog = openEditListDialog.value,
            onSave = {newName, isPublic-> listViewModel.onSaveEditList(newName, isPublic, openEditListDialog) },
            onCancel = {openEditListDialog.value = false})

        DeleteListDialog(
            openDeleteListDialog = openDeleteListDialog.value,
            onDelete = { listViewModel.onDeleteList(openDeleteListDialog, navController) },
            onCancel = { openDeleteListDialog.value = false })
    }
}