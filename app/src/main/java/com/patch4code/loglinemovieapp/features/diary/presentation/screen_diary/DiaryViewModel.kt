package com.patch4code.loglinemovieapp.features.diary.presentation.screen_diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.patch4code.loglinemovieapp.features.diary.domain.model.LoggedMovie
import com.patch4code.loglinemovieapp.room_database.LoggedMovieDao
import kotlinx.coroutines.launch

class DiaryViewModel(private val loggedMovieDao: LoggedMovieDao): ViewModel() {

    private val _diaryLogs = MutableLiveData<List<LoggedMovie>>()
    val diaryLogs: LiveData<List<LoggedMovie>> get() = _diaryLogs

    fun getDiaryLogs(){
        viewModelScope.launch {
            _diaryLogs.value = loggedMovieDao.getLoggedMovieListByDate()
        }
    }
}

class DiaryViewModelFactory(private val dao: LoggedMovieDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DiaryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DiaryViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}