package com.patch4code.loglinemovieapp.features.list.presentation.screen_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.patch4code.loglinemovieapp.features.core.domain.model.FilterOptions
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.domain.model.SortOption
import com.patch4code.loglinemovieapp.features.core.presentation.utils.FilterHelper
import com.patch4code.loglinemovieapp.features.core.presentation.utils.MovieHelper
import com.patch4code.loglinemovieapp.features.list.domain.model.ListSortOptions
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieInList
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieList
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieWithListItem
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

    private val _moviesInList = MutableLiveData<List<MovieWithListItem>>()
    val moviesInList: LiveData<List<MovieWithListItem>> get() = _moviesInList

    // Sets the movie list data based on id by calling the db and ordering with kotlin
    fun loadList(listId: String, sortOption: SortOption, filterOptions: FilterOptions) {
        if (sortOption !in ListSortOptions.options) {
            throw IllegalArgumentException("Unsupported sort option for List: $sortOption")
        }

        viewModelScope.launch {
            _movieList.value = movieListDao.getMovieListById(listId)
            val sortedItems = when (sortOption) {
                SortOption.ByPositionAsc -> movieInListDao.getMoviesInListOrderedByPositionAsc(listId)
                SortOption.ByTitleAsc -> movieInListDao.getMoviesInListOrderedByTitleAsc(listId)
                SortOption.ByTitleDesc -> movieInListDao.getMoviesInListOrderedByTitleDesc(listId)
                SortOption.ByReleaseDateAsc -> movieInListDao.getMoviesInListOrderedByReleaseDateAsc(listId)
                SortOption.ByReleaseDateDesc -> movieInListDao.getMoviesInListOrderedByReleaseDateDesc(listId)
                SortOption.ByAddedAsc -> movieInListDao.getMoviesInListOrderedByTimeAddedAsc(listId)
                SortOption.ByAddedDesc -> movieInListDao.getMoviesInListOrderedByTimeAddedDesc(listId)
                SortOption.ByPopularityAsc -> movieInListDao.getMoviesInListOrderedByPopularityAsc(listId)
                SortOption.ByPopularityDesc -> movieInListDao.getMoviesInListOrderedByPopularityDesc(listId)
                SortOption.ByVoteAverageAsc -> movieInListDao.getMoviesInListOrderedByVoteAverageAsc(listId)
                SortOption.ByVoteAverageDesc -> movieInListDao.getMoviesInListOrderedByVoteAverageDesc(listId)
                else -> emptyList()
            }
            val filteredAndSortedListItems = filterList(sortedItems, filterOptions)
            _moviesInList.value = filteredAndSortedListItems
        }
    }

    private fun filterList(items: List<MovieWithListItem>, filterOptions: FilterOptions): List<MovieWithListItem> {
        return items.filter { movieWithListItem ->

            val movie = movieWithListItem.movie

            matchesGenre(movie.genreIds, filterOptions.selectedGenres) &&
                    matchesDecade(movie.releaseDate, filterOptions.selectedDecades) &&
                    matchesYear(movie.releaseDate, filterOptions.selectedYears) &&
                    matchesLanguage(movie.originalLanguage, filterOptions.selectedLanguages)
        }
    }

    // Helper function to check genres
    private fun matchesGenre(genreIds: List<Int>?, selectedGenres: List<Int>): Boolean {
        return selectedGenres.isEmpty() || genreIds?.any { selectedGenres.contains(it) } == true
    }

    // Helper function to check decades
    private fun matchesDecade(releaseDate: String?, selectedDecades: List<String>): Boolean {
        return selectedDecades.isEmpty() || selectedDecades.contains(FilterHelper.getDecadeFromReleaseDate(releaseDate))
    }

    // Helper function to check years
    private fun matchesYear(releaseDate: String?, selectedYears: List<String>): Boolean {
        return selectedYears.isEmpty() || selectedYears.contains(MovieHelper.extractYear(releaseDate))
    }

    // Helper function to check languages
    private fun matchesLanguage(language: String?, selectedLanguages: List<String>): Boolean {
        return selectedLanguages.isEmpty() || selectedLanguages.contains(language ?: "N/A")
    }

    // Adds a movie to the movie list calling the db
    fun addMovieToList(movie: Movie, sortOption: SortOption, filterOptions: FilterOptions) {
        val listId = _movieList.value?.id ?: return
        viewModelScope.launch {
            val highestListPosition = movieInListDao.getHighestPositionInList(listId) ?: -1

            val newMovieInList =  MovieInList(
                movieListId = listId,
                movieId = movie.id,
                position = highestListPosition + 1,
                timeAdded = System.currentTimeMillis(),
            )
            movieInListDao.addMovieToList(newMovieInList, movie)
            movieListDao.updateListTimeUpdated(listId, System.currentTimeMillis())

            //Update
            loadList(movieList.value!!.id, sortOption, filterOptions)
        }
    }
    // Checks if a movie is already on the current list
    fun isMovieAlreadyOnList(movie: Movie): Boolean{
        val currentMoviesInList = _moviesInList.value ?: emptyList()
        return currentMoviesInList.any { it.movie.id == movie.id }
    }
    // Removes a movie from the movie list calling the db
    fun removeMovieFromList(movieId: Int, sortOption: SortOption, filterOptions: FilterOptions) {
        val listId = _movieList.value?.id ?: return
        viewModelScope.launch {
            movieInListDao.removeMovieFromList(listId, movieId)
            movieListDao.updateListTimeUpdated(listId, System.currentTimeMillis())

            loadList(movieList.value!!.id, sortOption, filterOptions)
        }
    }
    // Edits the movie list parameters calling the db
    fun editList(newTitle: String, newIsPublicState: Boolean){
        val listId = _movieList.value?.id ?: return
        viewModelScope.launch {
            movieListDao.editListParameters(listId, newTitle, newIsPublicState)
            movieListDao.updateListTimeUpdated(listId, System.currentTimeMillis())

            _movieList.value = movieListDao.getMovieListById(listId)
        }
    }
    // Deletes the movie list calling the db
    fun deleteList(){
        val listId = _movieList.value?.id ?: return
        viewModelScope.launch {
            movieListDao.deleteMovieListById(listId)
            movieInListDao.deleteAllMoviesFromList(listId)
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