package com.patch4code.loglinemovieapp.features.social.presentation.screen_public_reviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.coroutines.launch

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * PublicReviewDetailsViewModel - ViewModel responsible for managing public review details related actions.
 *
 * @author Patch4Code
 */
class PublicReviewDetailsViewModel: ViewModel() {

    private val _isYourReview = MutableLiveData<Boolean>()
    val isYourReview: LiveData<Boolean> get() = _isYourReview

    // Checks if the review belongs to the current user
    fun isYourReview(userId: String){
        val currentUser = ParseUser.getCurrentUser()
        if (currentUser != null) {
            _isYourReview.value = currentUser.objectId == userId
        } else {
            _isYourReview.value = false
        }
    }

    // Deletes a public review from Back4App
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