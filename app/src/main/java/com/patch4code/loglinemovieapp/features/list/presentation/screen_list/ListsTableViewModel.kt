package com.patch4code.loglinemovieapp.features.list.presentation.screen_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieList
import com.patch4code.loglinemovieapp.room_database.MovieListDao
import kotlinx.coroutines.launch

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ListsTableView - ViewModel responsible for managing data related to the lists table screen.
 * Functionality for deleting and adding lists communicating with db.
 *
 * @param movieListDao The DAO for accessing movie list data from the db.
 * @author Patch4Code
 */
class ListsTableViewModel(private val movieListDao: MovieListDao) : ViewModel(){

    private val _userMovieLists = MutableLiveData<List<MovieList>>()
    val userMovieLists: LiveData<List<MovieList>> get() = _userMovieLists

    // Gets the list of user movie lists from the database.
    fun updateUserMovieLists(){
        viewModelScope.launch {
            _userMovieLists.value = movieListDao.getMovieLists()
        }
    }
    // Adds a new movie list to the database and updates the user movie lists
    fun addUserMovieList(movieList: MovieList) {
        viewModelScope.launch {
            movieListDao.upsertMovieList(movieList)
            _userMovieLists.value = movieListDao.getMovieLists()
        }
    }
    // Removes a movie list from the database and updates the user movie lists
    fun removeUserMovieList(movieList: MovieList) {
        viewModelScope.launch {
            movieListDao.deleteMovieList(movieList)
            _userMovieLists.value = movieListDao.getMovieLists()
        }
    }
}

// Factory-class for creating ListsTableViewModel instances to manage access to the database
class ListsTableViewModelFactory(private val movieListDao: MovieListDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListsTableViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListsTableViewModel(movieListDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}