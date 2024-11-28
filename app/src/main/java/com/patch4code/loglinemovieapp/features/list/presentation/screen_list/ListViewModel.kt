package com.patch4code.loglinemovieapp.features.list.presentation.screen_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.list.domain.model.ListSortOptions
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieInList
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieList
import com.patch4code.loglinemovieapp.room_database.MovieInListDao
import com.patch4code.loglinemovieapp.room_database.MovieListDao
import kotlinx.coroutines.launch

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ListsTableView - ViewModel responsible for managing data related to the list screen.
 * Provides methods for adding and removing movies and editing and publishing the movie list.
 *
 * @param movieListDao The DAO for accessing movie list data from the db.
 * @author Patch4Code
 */
class ListViewModel(private val movieListDao: MovieListDao, private val movieInListDao: MovieInListDao): ViewModel() {

    private val _movieList = MutableLiveData<MovieList>()
    val movieList: LiveData<MovieList> get() = _movieList

    private val _moviesInList = MutableLiveData<List<MovieInList>>()
    val moviesInList: LiveData<List<MovieInList>> get() = _moviesInList

    // Sets the movie list data based on id by calling the db and ordering with kotlin
    fun getList(movieList: MovieList, sortOption: ListSortOptions) {
        viewModelScope.launch {
            _movieList.value = movieListDao.getMovieListById(movieList.id)

            val sortedList = when (sortOption) {
                ListSortOptions.ByPositionAsc -> movieInListDao.getMoviesInListOrderedByPositionAsc(movieList.id)
                ListSortOptions.ByPositionDesc -> movieInListDao.getMoviesInListOrderedByPositionDesc(movieList.id)
                ListSortOptions.ByTitleAsc -> movieInListDao.getMoviesInListOrderedByTitleAsc(movieList.id)
                ListSortOptions.ByTitleDesc -> movieInListDao.getMoviesInListOrderedByTitleDesc(movieList.id)
                ListSortOptions.ByReleaseDateAsc -> movieInListDao.getMoviesInListOrderedByReleaseDateAsc(movieList.id)
                ListSortOptions.ByReleaseDateDesc -> movieInListDao.getMoviesInListOrderedByReleaseDateDesc(movieList.id)
                ListSortOptions.ByTimeAddedAsc -> movieInListDao.getMoviesInListOrderedByTimeAddedAsc(movieList.id)
                ListSortOptions.ByTimeAddedDesc -> movieInListDao.getMoviesInListOrderedByTimeAddedDesc(movieList.id)
            }
            _moviesInList.value = sortedList
        }
    }
    // Adds a movie to the movie list calling the db
    fun addMovieToList(movie: Movie) {
        val listId = _movieList.value?.id ?: ""
        viewModelScope.launch {
            listId?.let { movieListDao.addMovieToList(it, movie) }
            _movieList.value = listId?.let { movieListDao.getMovieListById(it) }

            val highestListPosition = movieInListDao.getHighestPositionInList(listId) ?: -1
            val newMovieInList =
                MovieInList(
                    movieListId = listId,
                    position = highestListPosition+1,
                    movieId = movie.id,
                    title = movie.title,
                    releaseDate = movie.releaseDate,
                    posterUrl = movie.posterUrl,
                    timeAdded = System.currentTimeMillis()
                )
            movieInListDao.upsertMovieInList(newMovieInList)
            //getList()
        }
    }
    // Checks if a movie is already on the current list
    fun isMovieAlreadyOnList(movie: Movie): Boolean{
        val movieList = _movieList.value?.movies
        return movieList?.contains(movie) ?: false
    }
    // Removes a movie from the movie list calling the db
    fun removeMovieFromList(movieId: Int) {
        val listId = _movieList.value?.id ?: ""
        viewModelScope.launch {
            listId?.let { movieListDao.removeMovieFromList(it, movieId) }
            _movieList.value = listId?.let { movieListDao.getMovieListById(it) }

            movieInListDao.removeMovieFromList(listId, movieId)
        }
    }
    // Edits the movie list parameters calling the db
    fun editList(newTitle: String, newIsPublicState: Boolean){
        val listId = _movieList.value?.id
        viewModelScope.launch {
            listId?.let { movieListDao.editListParameters(it, newTitle, newIsPublicState) }
            _movieList.value = listId?.let { movieListDao.getMovieListById(it) }
        }
    }
    // Deletes the movie list calling the db
    fun deleteList(){
        val listId = _movieList.value?.id
        viewModelScope.launch {
            listId?.let { movieListDao.deleteMovieListById(it) }
        }
    }
}

// Factory-class for creating ListViewModel instances to manage access to the database
class ListViewModelFactory(private val movieListDao: MovieListDao, private val movieInListDao: MovieInListDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListViewModel(movieListDao, movieInListDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}