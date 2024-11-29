package com.patch4code.loglinemovieapp.features.list.presentation.utils

import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.list.domain.model.ListSortOptions
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieInList
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieList
import com.patch4code.loglinemovieapp.features.list.presentation.screen_list.ListViewModel
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ListDialogsExtensions - Provides extension functions for handling list dialogs and actions.
 *
 * @author Patch4Code
 */
object ListDialogsExtensions{

    // Deletes a movie from a list calling the ListViewModel
    fun ListViewModel.onDeleteMovieFromList(
        movieToDelete: MutableState<MovieInList?>,
        openDeleteMovieDialog: MutableState<Boolean>,
        sortOption: ListSortOptions
    ) {
        movieToDelete.value?.let { removeMovieFromList(it.movieId, sortOption) }
        movieToDelete.value = null
        openDeleteMovieDialog.value = false
    }
    // Cancels the deletion of a movie from a list
    fun onCancelDeleteMovieFromList(openDeleteMovieDialog: MutableState<Boolean>, movieList:  MovieList?, navController: NavController){
        openDeleteMovieDialog.value = false

        //workaround with scene reload
        val listId = movieList?.id ?: return
        navController.navigate(Screen.ListScreen.withArgs(listId)){
            popUpTo(Screen.ListsTableScreen.route){
                inclusive = false
            }
        }
    }

    // show the edit list dialog
    fun onEditListBottomSheet(showBottomSheet: MutableState<Boolean>, openEditListDialog: MutableState<Boolean>){
        showBottomSheet.value = false
        openEditListDialog.value = true
    }
    // show the delete list dialog
    fun onDeleteListBottomSheet(showBottomSheet: MutableState<Boolean>, openDeleteListDialog: MutableState<Boolean>){
        showBottomSheet.value = false
        openDeleteListDialog.value = true
    }

    // save the edited list
    fun ListViewModel.onSaveEditList(newName: String, isPublic: Boolean, openEditListDialog: MutableState<Boolean>) {
        openEditListDialog.value = false
        editList(newName, isPublic)
    }

    // delete the list
    fun ListViewModel.onDeleteList(openDeleteListDialog: MutableState<Boolean>, navController: NavController){
        deleteList()
        openDeleteListDialog.value = false
        navController.navigate(Screen.ListsTableScreen.route){
            popUpTo(Screen.ListsTableScreen.route){
                inclusive = true
            }
        }
    }
}