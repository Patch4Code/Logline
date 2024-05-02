package com.patch4code.loglinemovieapp.features.social.presentation.screen_public_lists

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
 * PublicListViewModel - ViewModel responsible for managing public list data.
 *
 * @author Patch4Code
 */
class PublicListViewModel: ViewModel() {

    private val _isYourList = MutableLiveData<Boolean>()
    val isYourList: LiveData<Boolean> get() = _isYourList

    // Checks if the list belongs to the current user
    fun isYourLis(userId: String){
        val currentUser = ParseUser.getCurrentUser()
        if (currentUser != null) {
            _isYourList.value = currentUser.objectId == userId
        } else {
            _isYourList.value = false
        }
    }

    // Deletes a public list from Back4App
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