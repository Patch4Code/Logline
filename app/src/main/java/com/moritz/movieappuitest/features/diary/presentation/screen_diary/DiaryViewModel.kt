package com.moritz.movieappuitest.features.diary.presentation.screen_diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moritz.movieappuitest.features.diary.domain.model.LoggedMovie
import com.moritz.movieappuitest.features.diary.domain.model.LoggedMoviesDummy

class DiaryViewModel: ViewModel() {

    private val _diaryLogs = MutableLiveData<List<LoggedMovie>>()
    val diaryLogs: LiveData<List<LoggedMovie>> get() = _diaryLogs


    init{
        getDiaryLogs()
    }

    private fun getDiaryLogs(){
        _diaryLogs.value = LoggedMoviesDummy
    }

}