package com.moritz.movieappuitest.features.movie.presentation.screen_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moritz.movieappuitest.features.core.domain.model.Movie
import com.moritz.movieappuitest.features.list.domain.model.MovieList
import com.moritz.movieappuitest.features.list.domain.model.userMovieListsDummy

class AddToListViewModel: ViewModel() {

    private val _movieToAdd = MutableLiveData<Movie>()
    val movieToAdd: LiveData<Movie> get() = _movieToAdd

    private val _userMovieLists = MutableLiveData<List<MovieList>>()
    val userMovieLists: LiveData<List<MovieList>> get() = _userMovieLists

    init {
        updateUserMovieLists()
    }
    private fun updateUserMovieLists(){
        _userMovieLists.value = userMovieListsDummy.toList() //dummy data
    }

    fun setMovieToAdd(movie: Movie){
        _movieToAdd.value = movie
    }



}