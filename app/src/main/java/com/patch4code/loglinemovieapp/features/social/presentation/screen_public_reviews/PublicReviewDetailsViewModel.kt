package com.patch4code.loglinemovieapp.features.social.presentation.screen_public_reviews

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.parse.ParseUser

class PublicReviewDetailsViewModel: ViewModel() {

    private val _isYourReview = MutableLiveData<Boolean>()
    val isYourReview: LiveData<Boolean> get() = _isYourReview

    fun isYourReview(userId: String){
        val currentUser = ParseUser.getCurrentUser()
        _isYourReview.value = currentUser.objectId == userId
        Log.e("PublicReviewDetailsViewModel", "userId: $userId - currentUser.Id: ${currentUser.objectId}")
    }
}