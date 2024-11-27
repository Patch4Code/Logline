package com.patch4code.loglinemovieapp.features.my_movies.presentation.screen_my_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieUserData
import com.patch4code.loglinemovieapp.features.my_movies.domain.model.MyMoviesSortOption
import com.patch4code.loglinemovieapp.room_database.MovieUserDataDao
import kotlinx.coroutines.launch

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MyMoviesViewModel - ViewModel for managing movie user data.
 *
 * @param dao MovieUserDataDao instance for accessing movie user data from the db.
 * @author Patch4Code
 */
class MyMoviesViewModel(private val dao: MovieUserDataDao): ViewModel() {

    private val _myUserDataList = MutableLiveData<List<MovieUserData>>()
    val myUserDataList: LiveData<List<MovieUserData>> get() = _myUserDataList

    fun getWatchedMovies(sortOption: MyMoviesSortOption) {
        viewModelScope.launch {
            val sortedList = when (sortOption) {
                MyMoviesSortOption.ByAddedDesc -> dao.getWatchedMoviesOrderedByAddedDesc()
                MyMoviesSortOption.ByAddedAsc -> dao.getWatchedMoviesOrderedByAddedAsc()
                MyMoviesSortOption.ByTitleAsc -> dao.getWatchedMoviesOrderedByTitleAsc()
                MyMoviesSortOption.ByTitleDesc -> dao.getWatchedMoviesOrderedByTitleDesc()
                MyMoviesSortOption.ByReleaseDateDesc -> dao.getWatchedMoviesOrderedByReleaseDateDesc()
                MyMoviesSortOption.ByReleaseDateAsc -> dao.getWatchedMoviesOrderedByReleaseDateAsc()
                MyMoviesSortOption.ByRatingDesc -> dao.getWatchedMoviesOrderedByRatingDesc()
                MyMoviesSortOption.ByRatingAsc -> dao.getWatchedMoviesOrderedByRatingAsc()
            }
            _myUserDataList.value = sortedList
        }
    }
}

// Factory-class for creating MyMoviesViewModel instances to manage access to the database
class MyMoviesViewModelFactory(private val dao: MovieUserDataDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyMoviesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyMoviesViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}