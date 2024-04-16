package com.patch4code.loglinemovieapp.features.social.presentation.screen_public_reviews

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.coroutines.launch

class PublicReviewDetailsViewModel: ViewModel() {

    private val _isYourReview = MutableLiveData<Boolean>()
    val isYourReview: LiveData<Boolean> get() = _isYourReview

    fun isYourReview(userId: String){
        val currentUser = ParseUser.getCurrentUser()
        _isYourReview.value = currentUser.objectId == userId
        Log.e("PublicReviewDetailsViewModel", "userId: $userId - currentUser.Id: ${currentUser.objectId}")
    }

    fun deletePublicReview(objectId: String, onSuccess:()->Unit, onError:(error: Exception)->Unit){
        viewModelScope.launch {
            val query = ParseQuery<ParseObject>("LoggedMovie")
            query.getInBackground(objectId){ loggedMovie, getError->
                if (getError == null) {
                    loggedMovie.deleteInBackground{deleteError->
                        if (deleteError == null){
                            onSuccess()
                        }else{
                            onError(deleteError)
                        }
                    }
                }else{
                    onError(getError)
                }
            }
        }
    }
}