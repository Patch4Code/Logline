package com.patch4code.loglinemovieapp.features.social.presentation.screen_public_lists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.coroutines.launch

class PublicListViewModel: ViewModel() {

    private val _isYourReview = MutableLiveData<Boolean>()
    val isYourReview: LiveData<Boolean> get() = _isYourReview

    fun isYourReview(userId: String){
        val currentUser = ParseUser.getCurrentUser()
        _isYourReview.value = currentUser.objectId == userId
    }

    fun deletePublicList(objectId: String, onSuccess:()->Unit, onError:(error: Exception)->Unit){
        viewModelScope.launch {
            val query = ParseQuery<ParseObject>("MovieList")
            query.getInBackground(objectId){ publicList, getError->
                if (getError == null) {
                    publicList.deleteInBackground{deleteError->
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