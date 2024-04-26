package com.patch4code.loglinemovieapp.features.diary.presentation.screen_diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.patch4code.loglinemovieapp.features.diary.domain.model.LoggedMovie
import com.patch4code.loglinemovieapp.room_database.LoggedMovieDao
import kotlinx.coroutines.launch

/**
 * APACHE LICENSE, VERSION 2.0 (https://www.apache.org/licenses/LICENSE-2.0)
 *
 * DiaryViewModel - ViewModel responsible for managing diary logs data.
 * Retrieves and provides diary logs from the db
 *
 * @author Patch4Code
 */
class DiaryViewModel(private val loggedMovieDao: LoggedMovieDao): ViewModel() {

    private val _diaryLogs = MutableLiveData<List<LoggedMovie>>()
    val diaryLogs: LiveData<List<LoggedMovie>> get() = _diaryLogs

    // function to get all diary entry's from the db
    fun getDiaryLogs(){
        viewModelScope.launch {
            _diaryLogs.value = loggedMovieDao.getLoggedMovieListByDate()
        }
    }
}

// Factory-class for creating DiaryViewModel instances to manage access to the database
class DiaryViewModelFactory(private val dao: LoggedMovieDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DiaryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DiaryViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}