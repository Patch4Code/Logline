package com.patch4code.loglinemovieapp.features.list.presentation.utils

import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieList
import com.patch4code.loglinemovieapp.features.list.presentation.screen_list.ListsTableViewModel
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen

object ListsTableContentExtensions{

    fun ListsTableViewModel.onAddList(listName: String, isPublic: Boolean, openAddListDialog: MutableState<Boolean>
    ) {
        openAddListDialog.value = false
        addUserMovieList(MovieList(listName, isPublic, mutableListOf()))
    }

    //Delete list and close dialog
    fun ListsTableViewModel.onDeleteList(listToDelete: MutableState<MovieList?>, openDeleteListDialog: MutableState<Boolean>) {
        listToDelete.value?.let { list ->
            removeUserMovieList(list)
        }
        listToDelete.value = null
        openDeleteListDialog.value = false
    }

    //Close DeleteDialog with workaround for slide problem
    fun onCancelDeleteList(openDeleteListDialog: MutableState<Boolean>, navController: NavController) {
        openDeleteListDialog.value = false

        // workaround with scene reload
        navController.navigate(Screen.ListsTableScreen.route) {
            popUpTo(Screen.ListsTableScreen.route) {
                inclusive = true
            }
        }
    }
}