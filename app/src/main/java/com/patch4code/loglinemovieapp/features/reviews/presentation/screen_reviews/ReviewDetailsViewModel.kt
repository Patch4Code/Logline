package com.patch4code.loglinemovieapp.features.reviews.presentation.screen_reviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.patch4code.loglinemovieapp.features.diary.domain.model.MovieWithLog
import com.patch4code.loglinemovieapp.room_database.LoggedMovieDao
import kotlinx.coroutines.launch

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ReviewDetailsViewModel - ViewModel responsible for managing review details.
 *
 * @param loggedMovieDao The DAO for accessing logged movie data from the db.
 * @author Patch4Code
 */
class ReviewDetailsViewModel(private val loggedMovieDao: LoggedMovieDao): ViewModel() {

    private val _currentReviewedLog = MutableLiveData<MovieWithLog>()
    val currentReviewedLog: LiveData<MovieWithLog> get() = _currentReviewedLog

    // Retrieves log data for a given logId by accessing the db
    fun setCurrentReviewedLog(reviewedLogId: String){
        viewModelScope.launch {
            _currentReviewedLog.value = loggedMovieDao.getLoggedMovieById(reviewedLogId)
        }
    }
}

// Factory-class for creating ReviewDetailsViewModel instances to manage access to the database
class ReviewDetailsViewModelFactory(private val loggedMovieDao: LoggedMovieDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReviewDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReviewDetailsViewModel(loggedMovieDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
