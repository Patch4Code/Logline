package com.patch4code.loglinemovieapp.features.diary.presentation.screen_diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
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
    fun getDiaryLogs(sortOption: DiaryAndReviewSortOptions){
        viewModelScope.launch {
           val sortedList = when (sortOption) {
               DiaryAndReviewSortOptions.ByAddedDesc -> loggedMovieDao.getLoggedMoviesOrderedByDateDesc()
               DiaryAndReviewSortOptions.ByAddedAsc -> loggedMovieDao.getLoggedMoviesOrderedByDateAsc()
               DiaryAndReviewSortOptions.ByTitleAsc -> loggedMovieDao.getLoggedMoviesOrderedByTitleAsc()
               DiaryAndReviewSortOptions.ByTitleDesc -> loggedMovieDao.getLoggedMoviesOrderedByTitleDesc()
               DiaryAndReviewSortOptions.ByReleaseDateDesc -> loggedMovieDao.getLoggedMoviesOrderedByReleaseDateDesc()
               DiaryAndReviewSortOptions.ByReleaseDateAsc -> loggedMovieDao.getLoggedMoviesOrderedByReleaseDateAsc()
               DiaryAndReviewSortOptions.ByRatingDesc -> loggedMovieDao.getLoggedMoviesOrderedByRatingDesc()
               DiaryAndReviewSortOptions.ByRatingAsc -> loggedMovieDao.getLoggedMoviesOrderedByRatingAsc()
           }
            _diaryLogs.value = sortedList
        }
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