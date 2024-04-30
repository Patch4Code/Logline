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

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * AddToListViewModel - ViewModel responsible for adding movies to a list from the MovieView.
 *
 * @param movieListDao The DAO for accessing movie list data from the db.
 * @author Patch4Code
 */
class AddToListViewModel(private val movieListDao: MovieListDao): ViewModel() {

    private val _movieToAdd = MutableLiveData<Movie>()

    private val _userMovieLists = MutableLiveData<List<MovieList>>()
    val userMovieLists: LiveData<List<MovieList>> get() = _userMovieLists

    // Updates the user's movie lists by accessing the db
    fun updateUserMovieLists(){
        viewModelScope.launch {
            _userMovieLists.value = movieListDao.getMovieLists()
        }
    }

    // Sets the movie to be added to a list.
    fun setMovieToAdd(movie: Movie){
        _movieToAdd.value = movie
    }

    // Adds the movie to the specified list by accessing the db.
    fun addMovieToList(list: MovieList?){
        if(list != null){
            val listId = list.id
            viewModelScope.launch {
                _movieToAdd.value?.let { movieListDao.addMovieToList(listId, it) }
            }
        }
    }
}

// Factory-class for creating AddToListViewModel instances to manage access to the database
class AddToListViewModelFactory(private val movieListDao: MovieListDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddToListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddToListViewModel(movieListDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}