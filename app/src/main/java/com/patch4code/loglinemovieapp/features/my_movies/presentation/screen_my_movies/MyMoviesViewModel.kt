package com.patch4code.loglinemovieapp.features.my_movies.presentation.screen_my_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.patch4code.loglinemovieapp.features.core.domain.model.FilterOptions
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieWithUserData
import com.patch4code.loglinemovieapp.features.core.domain.model.SortOption
import com.patch4code.loglinemovieapp.features.core.presentation.utils.FilterHelper
import com.patch4code.loglinemovieapp.features.core.presentation.utils.MovieHelper
import com.patch4code.loglinemovieapp.features.my_movies.domain.model.MyMoviesSortOptions
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

    private val _myMoviesItems = MutableLiveData<List<MovieWithUserData>>()
    val myMoviesItems: LiveData<List<MovieWithUserData>> get() = _myMoviesItems

    fun loadWatchedMovies(sortOption: SortOption, filterOptions: FilterOptions) {
        if (sortOption !in MyMoviesSortOptions.options) {
            throw IllegalArgumentException("Unsupported sort option for MyMovies: $sortOption")
        }

        viewModelScope.launch {
            val sortedItems = when (sortOption) {
                SortOption.ByAddedDesc -> dao.getWatchedMoviesOrderedByAddedDesc()
                SortOption.ByAddedAsc -> dao.getWatchedMoviesOrderedByAddedAsc()
                SortOption.ByTitleAsc -> dao.getWatchedMoviesOrderedByTitleAsc()
                SortOption.ByTitleDesc -> dao.getWatchedMoviesOrderedByTitleDesc()
                SortOption.ByReleaseDateDesc -> dao.getWatchedMoviesOrderedByReleaseDateDesc()
                SortOption.ByReleaseDateAsc -> dao.getWatchedMoviesOrderedByReleaseDateAsc()
                SortOption.ByRatingDesc -> dao.getWatchedMoviesOrderedByRatingDesc()
                SortOption.ByRatingAsc -> dao.getWatchedMoviesOrderedByRatingAsc()
                SortOption.ByPopularityDesc -> dao.getWatchedMoviesOrderedByPopularityDesc()
                SortOption.ByPopularityAsc -> dao.getWatchedMoviesOrderedByPopularityAsc()
                SortOption.ByVoteAverageDesc -> dao.getWatchedMoviesOrderedByVoteAverageDesc()
                SortOption.ByVoteAverageAsc -> dao.getWatchedMoviesOrderedByVoteAverageAsc()

                else -> emptyList()
            }
            val filteredAndSortedMyMoviesItems = filterMyMoviesItems(sortedItems, filterOptions)
            _myMoviesItems.value = filteredAndSortedMyMoviesItems
        }
    }

    private fun filterMyMoviesItems(items: List<MovieWithUserData>, filterOptions: FilterOptions): List<MovieWithUserData> {
        return items.filter { item ->
            val movie = item.movie
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