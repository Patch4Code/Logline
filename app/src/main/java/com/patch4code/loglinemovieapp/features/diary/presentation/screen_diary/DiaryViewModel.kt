package com.patch4code.loglinemovieapp.features.diary.presentation.screen_diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.patch4code.loglinemovieapp.features.core.domain.model.FilterOptions
import com.patch4code.loglinemovieapp.features.core.domain.model.SortOption
import com.patch4code.loglinemovieapp.features.core.presentation.utils.FilterHelper
import com.patch4code.loglinemovieapp.features.core.presentation.utils.MovieHelper
import com.patch4code.loglinemovieapp.features.diary.domain.model.DiaryAndReviewSortOptions
import com.patch4code.loglinemovieapp.features.diary.domain.model.LoggedMovie
import com.patch4code.loglinemovieapp.room_database.LoggedMovieDao
import kotlinx.coroutines.launch

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * DiaryViewModel - ViewModel responsible for managing diary logs data.
 * Retrieves and provides diary logs from the db
 *
 * @param loggedMovieDao The DAO for accessing movie log data from the db.
 * @author Patch4Code
 */
class DiaryViewModel(private val loggedMovieDao: LoggedMovieDao): ViewModel() {

    private val _diaryLogs = MutableLiveData<List<LoggedMovie>>()
    val diaryLogs: LiveData<List<LoggedMovie>> get() = _diaryLogs

    // function to get all diary entry's from the db
    fun loadDiaryLogs(sortOption: SortOption, filterOptions: FilterOptions){
        if (sortOption !in DiaryAndReviewSortOptions.options) {
            throw IllegalArgumentException("Unsupported sort option for Diary: $sortOption")
        }

        viewModelScope.launch {
           val sortedItems = when (sortOption) {
               SortOption.ByAddedDesc -> loggedMovieDao.getLoggedMoviesOrderedByDateDesc()
               SortOption.ByAddedAsc -> loggedMovieDao.getLoggedMoviesOrderedByDateAsc()
               SortOption.ByReleaseDateDesc -> loggedMovieDao.getLoggedMoviesOrderedByReleaseDateDesc()
               SortOption.ByReleaseDateAsc -> loggedMovieDao.getLoggedMoviesOrderedByReleaseDateAsc()
               SortOption.ByRatingDesc -> loggedMovieDao.getLoggedMoviesOrderedByRatingDesc()
               SortOption.ByRatingAsc -> loggedMovieDao.getLoggedMoviesOrderedByRatingAsc()
               SortOption.ByTitleAsc -> loggedMovieDao.getLoggedMoviesOrderedByTitleAsc()
               SortOption.ByTitleDesc -> loggedMovieDao.getLoggedMoviesOrderedByTitleDesc()
               SortOption.ByPopularityDesc -> loggedMovieDao.getLoggedMoviesOrderedByPopularityDesc()
               SortOption.ByPopularityAsc -> loggedMovieDao.getLoggedMoviesOrderedByPopularityAsc()
               SortOption.ByVoteAverageDesc -> loggedMovieDao.getLoggedMoviesOrderedByVoteAverageDesc()
               SortOption.ByVoteAverageAsc -> loggedMovieDao.getLoggedMoviesOrderedByVoteAverageAsc()
               else -> emptyList()
           }
            val filteredAndSortedWatchDiaryLogs = filterDiaryLogs(sortedItems, filterOptions)
            _diaryLogs.value = filteredAndSortedWatchDiaryLogs
        }
    }

    private fun filterDiaryLogs(items: List<LoggedMovie>, filterOptions: FilterOptions): List<LoggedMovie> {
        return items.filter { item ->
            val movie = item.movie
            matchesGenre(movie?.genreIds, filterOptions.selectedGenres) &&
                    matchesDecade(movie?.releaseDate, filterOptions.selectedDecades) &&
                    matchesYear(movie?.releaseDate, filterOptions.selectedYears) &&
                    matchesLanguage(movie?.originalLanguage, filterOptions.selectedLanguages)
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

// Factory-class for creating DiaryViewModel instances to manage access to the database
class DiaryViewModelFactory(private val dao: LoggedMovieDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DiaryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DiaryViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}