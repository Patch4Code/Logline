package com.moritz.movieappuitest.features.list.presentation.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import com.moritz.movieappuitest.features.list.domain.model.MovieList
import com.moritz.movieappuitest.features.list.presentation.screen_list.ListsTableViewModel
import com.moritz.movieappuitest.features.navigation.domain.model.Screen

object ListsTableContentExtensions{

    //Check if list-name is unique and only add when that is the case
    fun ListsTableViewModel.onAddList(
        listName: String,
        isPublic: Boolean,
        openAddListDialog: MutableState<Boolean>,
        context: Context
    ) {

        if (isListNameUnique(listName)) {
            openAddListDialog.value = false
            addUserMovieList(MovieList(listName, isPublic, mutableListOf()))
        } else {
            Toast.makeText(context, "List name already exists!", Toast.LENGTH_LONG).show()
        }
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