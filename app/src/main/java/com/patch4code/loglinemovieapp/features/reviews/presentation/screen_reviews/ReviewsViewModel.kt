package com.patch4code.loglinemovieapp.features.reviews.presentation.screen_reviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.patch4code.loglinemovieapp.features.diary.domain.model.LoggedMovie
import com.patch4code.loglinemovieapp.features.diary.domain.model.LoggedMoviesDummy

class ReviewsViewModel: ViewModel(){

    private val _reviewedLogs = MutableLiveData<List<LoggedMovie>>()
    val reviewedLogs: LiveData<List<LoggedMovie>> get() = _reviewedLogs


    init{
        getReviewedLogs()
    }

    private fun getReviewedLogs(){
        _reviewedLogs.value = LoggedMoviesDummy.filter { it.review.isNotEmpty() }
    }
}