package com.patch4code.loglinemovieapp.features.diary.presentation.screen_diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.patch4code.loglinemovieapp.features.diary.domain.model.LoggedMovie
import com.patch4code.loglinemovieapp.room_database.LoggedMovieDao
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset

class DiaryEditElementViewModel(private val loggedMovieDao: LoggedMovieDao): ViewModel() {

    private val _diaryEntry = MutableLiveData<LoggedMovie>()
    val diaryEntry: LiveData<LoggedMovie> get() = _diaryEntry

    fun setDiaryEntryToEdit(diaryEntryId: String?){
        viewModelScope.launch {
            _diaryEntry.value = loggedMovieDao.getLoggedMovieById(diaryEntryId)
        }
    }

    fun updatedDiaryEntry(rating: Int, watchDate: LocalDateTime, review: String){
        viewModelScope.launch {
            _diaryEntry.value?.id?.let { diaryEntryId ->
                loggedMovieDao.updateLoggedMovie(diaryEntryId, rating, watchDate, review) }
        }
    }

    suspend fun adjustedDateTime(date: LocalDateTime): LocalDateTime{
        // Filter out entries with the same date as the given date - here with sample data
        val startOfDayMillis = date.toLocalDate().atStartOfDay().toEpochSecond(ZoneOffset.UTC)
        val endOfDayMillis = date.toLocalDate().atStartOfDay().plusDays(1).toEpochSecond(ZoneOffset.UTC)

        val sameDateLogs = loggedMovieDao.getLoggedMoviesWithSameDate(startOfDayMillis, endOfDayMillis)

        return if(sameDateLogs.isNotEmpty()){
            // Find the latest time among the existing entries
            val latestTime = sameDateLogs.maxByOrNull { it.date.toLocalTime() }?.date?.toLocalTime()
            // Adjust the time to 1 second after the latest time
            val adjustedTime = latestTime?.plusSeconds(1) ?: LocalTime.MIN
            // add the adjusted time to the date
            date.with(adjustedTime)
        }else{
            // If there are no existing entries on the same date, set the time to midnight
            date.withHour(0).withMinute(0).withSecond(0).withNano(0)
        }
    }

    fun deleteDiaryEntry(){
        viewModelScope.launch {
            _diaryEntry.value?.let { loggedMovieDao.deleteLoggedMovie(it) }
        }
    }
}


class DiaryEditElementViewModelFactory(private val loggedMovieDao: LoggedMovieDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DiaryEditElementViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DiaryEditElementViewModel(loggedMovieDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}