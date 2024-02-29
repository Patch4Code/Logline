package com.moritz.movieappuitest.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moritz.movieappuitest.dataclasses.Movie
import com.moritz.movieappuitest.dataclasses.MovieList
import com.moritz.movieappuitest.dataclasses.userMovieListsDummy

class ListViewModel: ViewModel() {

    private val _movieList = MutableLiveData<MovieList>()
    val movieList: LiveData<MovieList> get() = _movieList

    fun setList(movieList: MovieList) {
        Log.e("ListViewModel", "setList to: $movieList")
        _movieList.value = movieList
    }

    fun addMovieToList(movie: Movie) {

    }

    fun removeMovieFromList(movieId: Int) {
        val updatedMovies = _movieList.value?.movies.orEmpty().toMutableList()
        val listName = _movieList.value?.name
        updatedMovies.removeIf {it.id == movieId}

        // Change Dummy-Data and accordingly the local ViewModel Data
        userMovieListsDummy.find { it.name == listName }?.movies = updatedMovies
        userMovieListsDummy.find { it.name == listName }?.let { setList(it) }
    }
}
