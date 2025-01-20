package com.patch4code.loglinemovieapp.features.reviews.presentation.screen_reviews

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
import com.patch4code.loglinemovieapp.features.diary.domain.model.MovieWithLoggedData
import com.patch4code.loglinemovieapp.room_database.LoggedMovieDao
import kotlinx.coroutines.launch

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ReviewsViewModel - ViewModel responsible for accessing reviewed movie logs.
 *
 * @param loggedMovieDao The DAO for accessing logged movie data from the db.
 * @author Patch4Code
 */
class ReviewsViewModel(private val loggedMovieDao: LoggedMovieDao): ViewModel(){

    private val _reviewedLogs = MutableLiveData<List<MovieWithLoggedData>>()
    val reviewedLogs: LiveData<List<MovieWithLoggedData>> get() = _reviewedLogs

    fun getReviewedLogs(sortOption: SortOption, filterOptions: FilterOptions){
        if (sortOption !in DiaryAndReviewSortOptions.options) {
            throw IllegalArgumentException("Unsupported sort option for Diary: $sortOption")
        }

        viewModelScope.launch {

            val sortedItems = when (sortOption) {
                SortOption.ByAddedDesc -> loggedMovieDao.getReviewsOrderedByDateDesc()
                SortOption.ByAddedAsc -> loggedMovieDao.getReviewsOrderedByDateAsc()
                SortOption.ByReleaseDateDesc -> loggedMovieDao.getReviewsOrderedByReleaseDateDesc()
                SortOption.ByReleaseDateAsc -> loggedMovieDao.getReviewsOrderedByReleaseDateAsc()
                SortOption.ByRatingDesc -> loggedMovieDao.getReviewsOrderedByRatingDesc()
                SortOption.ByRatingAsc -> loggedMovieDao.getReviewsOrderedByRatingAsc()
                SortOption.ByTitleAsc -> loggedMovieDao.getReviewsOrderedByTitleAsc()
                SortOption.ByTitleDesc -> loggedMovieDao.getReviewsOrderedByTitleDesc()
                SortOption.ByPopularityDesc -> loggedMovieDao.getReviewsOrderedByPopularityDesc()
                SortOption.ByPopularityAsc -> loggedMovieDao.getReviewsOrderedByPopularityAsc()
                SortOption.ByVoteAverageDesc -> loggedMovieDao.getReviewsOrderedByVoteAverageDesc()
                SortOption.ByVoteAverageAsc -> loggedMovieDao.getReviewsOrderedByVoteAverageAsc()
                else -> emptyList()
            }
            val filteredAndSortedReviews = filterReviews(sortedItems, filterOptions)
            _reviewedLogs.value = filteredAndSortedReviews
        }
    }

    private fun filterReviews(items: List<MovieWithLoggedData>, filterOptions: FilterOptions): List<MovieWithLoggedData> {
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

// Factory-class for creating ReviewsViewModel instances to manage access to the database
class ReviewsViewModelFactory(private val loggedMovieDao: LoggedMovieDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReviewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReviewsViewModel(loggedMovieDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}