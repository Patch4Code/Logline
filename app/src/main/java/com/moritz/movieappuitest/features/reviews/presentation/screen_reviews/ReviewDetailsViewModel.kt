package com.moritz.movieappuitest.features.reviews.presentation.screen_reviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moritz.movieappuitest.features.diary.domain.model.LoggedMovie
import com.moritz.movieappuitest.features.diary.domain.model.LoggedMoviesDummy

class ReviewDetailsViewModel: ViewModel() {

    private val _currentReviewedLog = MutableLiveData<LoggedMovie>()
    val currentReviewedLog: LiveData<LoggedMovie> get() = _currentReviewedLog

    fun setCurrentReviewedLog(reviewedLogId: String){
        _currentReviewedLog.value = LoggedMoviesDummy.find { it.id == reviewedLogId }
    }

}