package com.patch4code.loglinemovieapp.features.reviews.presentation.screen_reviews

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.parse.ParseACL
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.presentation.utils.DateHelper
import com.patch4code.loglinemovieapp.features.core.presentation.utils.JSONHelper.toJson
import com.patch4code.loglinemovieapp.features.diary.domain.model.LoggedMovie
import com.patch4code.loglinemovieapp.room_database.LoggedMovieDao
import kotlinx.coroutines.launch

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ReviewDetailsViewModel - ViewModel responsible for managing review details.
 *
 * @param loggedMovieDao The DAO for accessing logged movie data from the db.
 * @author Patch4Code
 */
class ReviewDetailsViewModel(private val loggedMovieDao: LoggedMovieDao): ViewModel() {

    private val _currentReviewedLog = MutableLiveData<LoggedMovie>()
    val currentReviewedLog: LiveData<LoggedMovie> get() = _currentReviewedLog

    // Retrieves log data for a given logId by accessing the db
    fun setCurrentReviewedLog(reviewedLogId: String){
        viewModelScope.launch {
            _currentReviewedLog.value = loggedMovieDao.getLoggedMovieById(reviewedLogId)
        }
    }

    // makes the current review (reviewed log) public by communicating with Back4App
    fun makeReviewPublic(onSuccess:(publishStatus: String)->Unit, onError:(exception: Exception)->Unit){
        viewModelScope.launch {
            val publishStatus: String // is "Updated" or "Newly Published"
            try {
                val user = ParseUser.getCurrentUser()

                // finds out whether this review was published before or not and publishStatus is set accordingly
                val query = ParseQuery<ParseObject>("LoggedMovie")
                query.whereEqualTo("user", ParseUser.getCurrentUser())
                _currentReviewedLog.value?.id?.let { query.whereEqualTo("logId", it) }
                val existingLoggedMovie = query.find().firstOrNull()

                val loggedMovie: ParseObject
                if (existingLoggedMovie != null){
                    publishStatus = "Updated"
                    loggedMovie = existingLoggedMovie
                    Log.e("ReviewDetailsViewModel", "Update")
                }else{
                    publishStatus = "Newly Published"
                    loggedMovie = ParseObject("LoggedMovie")
                    Log.e("ReviewDetailsViewModel", "create")
                }

                loggedMovie.put("user", user)
                _currentReviewedLog.value?.id?.let { loggedMovie.put("logId", it) }
                _currentReviewedLog.value?.movie?.let { loggedMovie.put("movieId", it.id) }
                val movieJson = _currentReviewedLog.value?.movie?.toJson() ?: Movie().toJson()
                loggedMovie.put("movie", movieJson)
                val longTime = _currentReviewedLog.value?.date?.let { DateHelper.convertDateTimeToLong(it) }
                longTime?.let { loggedMovie.put("date", it) }
                _currentReviewedLog.value?.rating?.let { loggedMovie.put("rating", it) }
                _currentReviewedLog.value?.review?.let { loggedMovie.put("review", it) }

                val acl = ParseACL()
                acl.setWriteAccess(ParseUser.getCurrentUser(), true)
                acl.publicReadAccess = true
                loggedMovie.acl = acl

                loggedMovie.saveInBackground {exception->
                    if (exception == null){
                        onSuccess(publishStatus)
                        Log.e("ReviewDetailsViewModel", "Success ($publishStatus)")
                    }else{
                        onError(exception)
                        Log.e("ReviewDetailsViewModel", "Error: ", exception)
                    }
                }
            }catch (e: Exception){
                Log.e("ReviewDetailsViewModel", "Catch Error: ", e)
                onError(e)
            }
        }
    }
}

// Factory-class for creating ReviewDetailsViewModel instances to manage access to the database
class ReviewDetailsViewModelFactory(private val loggedMovieDao: LoggedMovieDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReviewDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReviewDetailsViewModel(loggedMovieDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
