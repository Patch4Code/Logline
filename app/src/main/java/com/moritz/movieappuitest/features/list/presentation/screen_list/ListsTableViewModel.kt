package com.moritz.movieappuitest.features.list.presentation.screen_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moritz.movieappuitest.features.list.domain.model.MovieList
import com.moritz.movieappuitest.features.list.domain.model.userMovieListsDummy

class ListsTableViewModel : ViewModel(){

    private val _userMovieLists = MutableLiveData<List<MovieList>>()
    val userMovieLists: LiveData<List<MovieList>> get() = _userMovieLists

    init {
        updateUserMovieLists()
    }

    private fun updateUserMovieLists(){
        _userMovieLists.value = userMovieListsDummy.toList() //dummy data
    }

    fun addUserMovieList(movieList: MovieList) {
        //add to dummy
        userMovieListsDummy.add(movieList)
        updateUserMovieLists()
    }

    fun removeUserMovieList(movieList: MovieList) {
        //remove from dummy
        userMovieListsDummy.remove(movieList)
        updateUserMovieLists()
    }

    fun isListNameUnique(listName: String): Boolean {
        return !userMovieListsDummy.any { it.name == listName }
    }
}