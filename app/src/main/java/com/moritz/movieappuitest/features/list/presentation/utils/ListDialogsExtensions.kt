package com.moritz.movieappuitest.features.list.presentation.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import com.moritz.movieappuitest.features.core.domain.model.Movie
import com.moritz.movieappuitest.features.core.presentation.utils.JSONHelper.toJson
import com.moritz.movieappuitest.features.list.domain.model.MovieList
import com.moritz.movieappuitest.features.list.presentation.screen_list.ListViewModel
import com.moritz.movieappuitest.features.navigation.domain.model.Screen
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


    fun ListViewModel.onSaveEditList(newName: String, isPublic: Boolean, openEditListDialog: MutableState<Boolean>, context: Context) {
        if(isListNameUnique(newName)){
            openEditListDialog.value = false
            editList(newName, isPublic)
        }else{
            Toast.makeText(context, "List name already exists!", Toast.LENGTH_LONG).show()
        }
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

