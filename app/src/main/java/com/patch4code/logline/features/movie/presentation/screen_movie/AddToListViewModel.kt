package com.patch4code.logline.features.movie.presentation.screen_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.patch4code.logline.features.core.domain.model.Movie
import com.patch4code.logline.features.list.domain.model.MovieInList
import com.patch4code.logline.features.list.domain.model.MovieList
import com.patch4code.logline.room_database.MovieInListDao
import com.patch4code.logline.room_database.MovieListDao
import kotlinx.coroutines.launch

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * AddToListViewModel - ViewModel responsible for adding movies to a list from the MovieView.
 *
 * @param movieListDao The DAO for accessing movie list data from the db.
 * @author Patch4Code
 */
class AddToListViewModel(private val movieListDao: MovieListDao, private val movieInListDao: MovieInListDao): ViewModel() {

    private val _movieToAdd = MutableLiveData<Movie>()

    private val _userMovieLists = MutableLiveData<List<MovieList>>()
    val userMovieLists: LiveData<List<MovieList>> get() = _userMovieLists

    private val _moviesInLists = MutableLiveData<List<MovieInList>>()
    val moviesInLists: LiveData<List<MovieInList>> get() = _moviesInLists

    // Updates the user's movie lists by accessing the db
    fun loadUserMovieLists(){
        viewModelScope.launch {
            _userMovieLists.value = movieListDao.getMovieListsOrderedByTimeUpdated()
            _moviesInLists.value = movieInListDao.getAllMoviesInLists()
        }
    }

    // Sets the movie to be added to a list.
    fun setMovieToAdd(movie: Movie){
        _movieToAdd.value = movie
    }

    fun isMovieAlreadyInList(listId: String, movieId: Int): Boolean{
        val currentMoviesInList: List<MovieInList> = _moviesInLists.value?.filter  { it.movieListId == listId} ?: emptyList()
        return currentMoviesInList.any { it.movieId == movieId }
    }

    // Adds the movie to the specified list by accessing the db.
    fun addMovieToList(list: MovieList?){
        val listId = list?.id ?: return
        viewModelScope.launch {
            val movie = _movieToAdd.value ?: return@launch
            val highestListPosition = movieInListDao.getHighestPositionInList(listId) ?: -1

            val newMovieInList = MovieInList(
                movieListId = listId,
                movieId = movie.id,
                position = highestListPosition + 1,
                timeAdded = System.currentTimeMillis()
            )
            movieInListDao.addMovieToList(newMovieInList, movie)

            movieListDao.updateListTimeUpdated(listId, System.currentTimeMillis())
        }
    }

    fun createMovieList(movieList: MovieList) {
        viewModelScope.launch {
            movieListDao.upsertMovieList(movieList)
            _userMovieLists.value = movieListDao.getMovieListsOrderedByTimeUpdated()
        }
    }
}

// Factory-class for creating AddToListViewModel instances to manage access to the database
class AddToListViewModelFactory(private val movieListDao: MovieListDao, private val movieInListDao: MovieInListDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddToListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddToListViewModel(movieListDao, movieInListDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}