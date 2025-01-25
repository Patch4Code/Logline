package com.patch4code.logline.features.watchlist.presentation.screen_watchlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.patch4code.logline.features.core.domain.model.FilterOptions
import com.patch4code.logline.features.core.domain.model.MovieWithUserData
import com.patch4code.logline.features.core.domain.model.SortOption
import com.patch4code.logline.features.core.presentation.utils.FilterHelper
import com.patch4code.logline.features.core.presentation.utils.MovieHelper
import com.patch4code.logline.features.watchlist.domain.model.WatchlistSortOptions
import com.patch4code.logline.room_database.MovieUserDataDao
import kotlinx.coroutines.launch

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * WatchlistViewModel - ViewModel responsible for for managing movie user data of movies on watchlist.
 *
 * @param dao MovieUserDataDao instance for accessing movie user data from the db.
 * @author Patch4Code
 */
class WatchlistViewModel(private val dao: MovieUserDataDao): ViewModel() {

    private val _watchlistItems = MutableLiveData<List<MovieWithUserData>>()
    val watchlistItems: LiveData<List<MovieWithUserData>> get() = _watchlistItems

    fun loadWatchlistItems(sortOption: SortOption, filterOptions: FilterOptions) {
        if (sortOption !in WatchlistSortOptions.options) {
            throw IllegalArgumentException("Unsupported sort option for Watchlist: $sortOption")
        }

        viewModelScope.launch {
            val sortedItems = when (sortOption) {
                SortOption.ByAddedDesc -> dao.getWatchlistItemsOrderedByAddedDesc()
                SortOption.ByAddedAsc -> dao.getWatchlistItemsOrderedByAddedAsc()
                SortOption.ByTitleAsc -> dao.getWatchlistItemsOrderedByTitleAsc()
                SortOption.ByTitleDesc -> dao.getWatchlistItemsOrderedByTitleDesc()
                SortOption.ByReleaseDateDesc -> dao.getWatchlistItemsOrderedByReleaseDateDesc()
                SortOption.ByReleaseDateAsc -> dao.getWatchlistItemsOrderedByReleaseDateAsc()
                SortOption.ByPopularityDesc -> dao.getWatchlistItemsOrderedByPopularityDesc()
                SortOption.ByPopularityAsc -> dao.getWatchlistItemsOrderedByPopularityAsc()
                SortOption.ByVoteAverageDesc -> dao.getWatchlistItemsOrderedByVoteAverageDesc()
                SortOption.ByVoteAverageAsc -> dao.getWatchlistItemsOrderedByVoteAverageAsc()
                else -> emptyList()
            }
            val filteredAndSortedWatchlistItems = filterWatchlistItems(sortedItems, filterOptions)
            _watchlistItems.value = filteredAndSortedWatchlistItems
        }
    }

    private fun filterWatchlistItems(items: List<MovieWithUserData>, filterOptions: FilterOptions): List<MovieWithUserData> {
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

// Factory-class for creating WatchlistViewModel instances to manage access to the database
class WatchlistViewModelFactory(private val dao: MovieUserDataDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WatchlistViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WatchlistViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}