package com.patch4code.loglinemovieapp.features.reviews.presentation.screen_reviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.patch4code.loglinemovieapp.features.diary.domain.model.LoggedMovie
import com.patch4code.loglinemovieapp.room_database.LoggedMovieDao
import kotlinx.coroutines.launch

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ReviewsViewModel - ViewModel responsible for accessing reviewed movie logs.
 *
 * @param loggedMovieDao The DAO for accessing logged movie data from the db.
 * @author Patch4Code
 */
class ReviewsViewModel(private val loggedMovieDao: LoggedMovieDao): ViewModel(){

    private val _reviewedLogs = MutableLiveData<List<LoggedMovie>>()
    val reviewedLogs: LiveData<List<LoggedMovie>> get() = _reviewedLogs

    fun getReviewedLogs(){
        viewModelScope.launch {
            _reviewedLogs.value = loggedMovieDao.getLoggedMovieWithReviewListByDate()
        }
    }
}

// Factory-class for creating ReviewsViewModel instances to manage access to the database
class ReviewsViewModelFactory(private val loggedMovieDao: LoggedMovieDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReviewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReviewsViewModel(loggedMovieDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}