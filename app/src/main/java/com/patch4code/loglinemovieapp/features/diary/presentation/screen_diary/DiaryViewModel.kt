package com.patch4code.loglinemovieapp.features.diary.presentation.screen_diary

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.patch4code.loglinemovieapp.features.diary.domain.model.LoggedMovie
import com.patch4code.loglinemovieapp.features.diary.domain.model.LoggedMoviesDummy

class DiaryViewModel: ViewModel() {

    private val _diaryLogs = MutableLiveData<List<LoggedMovie>>()
    val diaryLogs: LiveData<List<LoggedMovie>> get() = _diaryLogs


    init{
        getDiaryLogs()
    }

    private fun getDiaryLogs(){
        _diaryLogs.value = LoggedMoviesDummy

        //Log.e("DiaryViewModel","_diaryLogs: ${_diaryLogs.value}")
        printLoggedMoviesWithoutReview(_diaryLogs.value as MutableList<LoggedMovie>)
    }

    private fun printLoggedMoviesWithoutReview(loggedMovies: List<LoggedMovie>) {
        loggedMovies.forEach {
            Log.e("DiaryViewModel","Movie: ${it.movie.title}, Date: ${it.date}, Rating: ${it.rating}")
        }
        Log.e("DiaryViewModel", "---")
    }

}