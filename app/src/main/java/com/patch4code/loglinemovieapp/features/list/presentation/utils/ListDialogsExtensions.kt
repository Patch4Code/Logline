package com.patch4code.loglinemovieapp.features.list.presentation.utils

import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.presentation.utils.JSONHelper.toJson
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieList
import com.patch4code.loglinemovieapp.features.list.presentation.screen_list.ListViewModel
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import java.net.URLEncoder

object ListDialogsExtensions{

    fun ListViewModel.onDeleteMovieFromList(movieToDelete: MutableState<Movie?>, openDeleteMovieDialog: MutableState<Boolean>) {
        movieToDelete.value?.id?.let { movie->
            removeMovieFromList(movie)
        }
        movieToDelete.value = null
        openDeleteMovieDialog.value = false
    }
    fun onCancelDeleteMovieFromList(openDeleteMovieDialog: MutableState<Boolean>, movieList:  MovieList?, navController: NavController){
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


    fun onEditListBottomSheet(showBottomSheet: MutableState<Boolean>, openEditListDialog: MutableState<Boolean>){
        showBottomSheet.value = false
        openEditListDialog.value = true
    }
    fun onDeleteListBottomSheet(showBottomSheet: MutableState<Boolean>, openDeleteListDialog: MutableState<Boolean>){
        showBottomSheet.value = false
        openDeleteListDialog.value = true
    }


    fun ListViewModel.onSaveEditList(newName: String, isPublic: Boolean, openEditListDialog: MutableState<Boolean>) {
        openEditListDialog.value = false
        editList(newName, isPublic)
    }


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

