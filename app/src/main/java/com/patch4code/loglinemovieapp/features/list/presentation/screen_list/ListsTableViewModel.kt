package com.patch4code.loglinemovieapp.features.list.presentation.screen_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieList
import com.patch4code.loglinemovieapp.room_database.MovieListDao
import kotlinx.coroutines.launch

class ListsTableViewModel(private val movieListDao: MovieListDao) : ViewModel(){

    private val _userMovieLists = MutableLiveData<List<MovieList>>()
    val userMovieLists: LiveData<List<MovieList>> get() = _userMovieLists

    fun updateUserMovieLists(){
        viewModelScope.launch {
            _userMovieLists.value = movieListDao.getMovieLists()
        }
    }

    fun addUserMovieList(movieList: MovieList) {
        viewModelScope.launch {
            movieListDao.upsertMovieList(movieList)
            _userMovieLists.value = movieListDao.getMovieLists()
        }
    }

    fun removeUserMovieList(movieList: MovieList) {
        viewModelScope.launch {
            movieListDao.deleteMovieList(movieList)
            _userMovieLists.value = movieListDao.getMovieLists()
        }
    }
}


class ListsTableViewModelFactory(private val movieListDao: MovieListDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListsTableViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListsTableViewModel(movieListDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}