package com.moritz.movieappuitest.viewmodels

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
        _movieList.value = movieList
    }

    fun addMovieToList(movie: Movie) {

    }

    fun removeMovieFromList(movieId: Int) {

        val listName = _movieList.value?.name
        val listToRemoveFrom = userMovieListsDummy.find { it.name == listName }
        //listToRemoveFrom?.movies?.removeAll { it.id == movieId }



        //remove from dummy list

    }




}
