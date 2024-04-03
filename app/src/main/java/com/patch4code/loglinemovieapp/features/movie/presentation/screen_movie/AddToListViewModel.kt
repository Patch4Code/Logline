package com.patch4code.loglinemovieapp.features.movie.presentation.screen_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieList
import com.patch4code.loglinemovieapp.room_database.MovieListDao
import kotlinx.coroutines.launch

class AddToListViewModel(private val movieListDao: MovieListDao): ViewModel() {

    private val _movieToAdd = MutableLiveData<Movie>()

    private val _userMovieLists = MutableLiveData<List<MovieList>>()
    val userMovieLists: LiveData<List<MovieList>> get() = _userMovieLists


    fun updateUserMovieLists(){
        viewModelScope.launch {
            _userMovieLists.value = movieListDao.getMovieLists()
        }
    }

    fun setMovieToAdd(movie: Movie){
        _movieToAdd.value = movie
    }

    fun addMovieToList(list: MovieList?){
        if(list != null){
            val listId = list.id
            viewModelScope.launch {
                _movieToAdd.value?.let { movieListDao.addMovieToList(listId, it) }
            }
        }
    }
}


class AddToListViewModelFactory(private val movieListDao: MovieListDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddToListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddToListViewModel(movieListDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}