package com.patch4code.loglinemovieapp.features.reviews.presentation.screen_reviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.patch4code.loglinemovieapp.features.diary.domain.model.LoggedMovie
import com.patch4code.loglinemovieapp.room_database.LoggedMovieDao
import kotlinx.coroutines.launch

class ReviewDetailsViewModel(private val loggedMovieDao: LoggedMovieDao): ViewModel() {

    private val _currentReviewedLog = MutableLiveData<LoggedMovie>()
    val currentReviewedLog: LiveData<LoggedMovie> get() = _currentReviewedLog

    fun setCurrentReviewedLog(reviewedLogId: String){
        viewModelScope.launch {
            _currentReviewedLog.value = loggedMovieDao.getLoggedMovieById(reviewedLogId)
        }
    }
}


class ReviewDetailsViewModelFactory(private val loggedMovieDao: LoggedMovieDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReviewDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReviewDetailsViewModel(loggedMovieDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
