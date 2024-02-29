package com.moritz.movieappuitest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moritz.movieappuitest.dataclasses.MovieList
import com.moritz.movieappuitest.dataclasses.userMovieListsDummy

class ListsTableViewModel : ViewModel(){

    private val _userMovieLists = MutableLiveData<List<MovieList>>()
    val userMovieLists: LiveData<List<MovieList>> get() = _userMovieLists

    init {
        updateUserMovieLists()
    }

    fun updateUserMovieLists(){
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
}