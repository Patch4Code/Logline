package com.patch4code.loglinemovieapp.features.list.presentation.utils

import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.list.domain.model.ListTableSortOptions
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieList
import com.patch4code.loglinemovieapp.features.list.presentation.screen_list.ListsTableViewModel
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ListsTableContentExtensions - Provides extension functions for managing list table content.
 *
 * @author Patch4Code
 */
object ListsTableContentExtensions{

    // add a new list to the table
    fun ListsTableViewModel.onAddList(listName: String, openAddListDialog: MutableState<Boolean>, sortOption: ListTableSortOptions
    ) {
        val currentTime = System.currentTimeMillis()
        openAddListDialog.value = false
        addMovieList(MovieList(name = listName, timeCreated = currentTime, timeUpdated = currentTime), sortOption)
    }

    //Delete list and close dialog
    fun ListsTableViewModel.onDeleteList(listToDelete: MutableState<MovieList?>, openDeleteListDialog: MutableState<Boolean> , sortOption: ListTableSortOptions
    ) {
        listToDelete.value?.let { list ->
            removeMovieList(list.id, sortOption)
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