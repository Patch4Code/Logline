package com.patch4code.loglinemovieapp.features.reviews.presentation.screen_reviews

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.presentation.utils.DateHelper
import com.patch4code.loglinemovieapp.features.core.presentation.utils.JSONHelper.toJson
import com.patch4code.loglinemovieapp.features.diary.domain.model.LoggedMovie
import com.patch4code.loglinemovieapp.room_database.LoggedMovieDao
import kotlinx.coroutines.launch

class ReviewDetailsViewModel(private val loggedMovieDao: LoggedMovieDao): ViewModel() {

    private val _currentReviewedLog = MutableLiveData<LoggedMovie>()
    val currentReviewedLog: LiveData<LoggedMovie> get() = _currentReviewedLog

    fun setCurrentReviewedLog(reviewedLogId: String){
        viewModelScope.launch {
            _currentReviewedLog.value = loggedMovieDao.getLoggedMovieById(reviewedLogId)
        }
    }

    fun makeReviewPublic(onSuccess:(alreadyExistingText: String)->Unit, onError:(exception: Exception)->Unit){
        viewModelScope.launch {
            val publishStatus: String
            try {
                val user = ParseUser.getCurrentUser()

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
                val movieJson = _currentReviewedLog.value?.movie?.toJson() ?: Movie()
                loggedMovie.put("movie", movieJson)
                val longTime = _currentReviewedLog.value?.date?.let { DateHelper.convertDateTimeToLong(it) }
                longTime?.let { loggedMovie.put("date", it) }
                _currentReviewedLog.value?.rating?.let { loggedMovie.put("rating", it) }
                _currentReviewedLog.value?.review?.let { loggedMovie.put("review", it) }

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


class ReviewDetailsViewModelFactory(private val loggedMovieDao: LoggedMovieDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReviewDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReviewDetailsViewModel(loggedMovieDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
