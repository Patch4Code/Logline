package com.patch4code.loglinemovieapp.features.reviews.presentation.screen_reviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.patch4code.loglinemovieapp.features.diary.domain.model.LoggedMovie
import com.patch4code.loglinemovieapp.room_database.LoggedMovieDao
import kotlinx.coroutines.launch

class ReviewsViewModel(private val loggedMovieDao: LoggedMovieDao): ViewModel(){

    private val _reviewedLogs = MutableLiveData<List<LoggedMovie>>()
    val reviewedLogs: LiveData<List<LoggedMovie>> get() = _reviewedLogs

    fun getReviewedLogs(){
        viewModelScope.launch {
            _reviewedLogs.value = loggedMovieDao.getLoggedMovieWithReviewListByDate()
        }
    }
}


class ReviewsViewModelFactory(private val loggedMovieDao: LoggedMovieDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReviewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReviewsViewModel(loggedMovieDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}