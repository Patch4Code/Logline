package com.patch4code.loglinemovieapp.features.diary.presentation.screen_diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.patch4code.loglinemovieapp.features.diary.domain.model.LoggedMovie
import com.patch4code.loglinemovieapp.features.diary.domain.model.LoggedMoviesDummy
import java.time.LocalDateTime
import java.time.LocalTime

class DiaryEditElementViewModel: ViewModel() {

    private val _diaryEntry = MutableLiveData<LoggedMovie>()
    val diaryEntry: LiveData<LoggedMovie> get() = _diaryEntry

    fun setDiaryEntryToEdit(diaryEntryId: String?){
        _diaryEntry.value = LoggedMoviesDummy.find { it.id == diaryEntryId }
    }

    fun updatedDiaryEntry(rating: Int, watchDate: LocalDateTime, review: String){
        val diaryEntryId = _diaryEntry.value?.id

        //Here only updates dummy temporary (later probably use an id for identification)
        val updatedElement = LoggedMoviesDummy.find { it.id == diaryEntryId }
        updatedElement?.let {
            it.date = watchDate
            it.rating = rating
            it.review = review
        }
        _diaryEntry.value = updatedElement!!
    }

    fun adjustedDateTime(date: LocalDateTime): LocalDateTime{
        // Filter out entries with the same date as the given date - here with sample data
        val sameDateLogs = LoggedMoviesDummy.filter { it.date.toLocalDate() == date.toLocalDate() }

        return if(sameDateLogs.isNotEmpty()){
            // Find the latest time among the existing entries
            val latestTime = sameDateLogs.maxByOrNull { it.date.toLocalTime() }?.date?.toLocalTime()
            // Adjust the time to 1 second after the latest time
            val adjustedTime = latestTime?.plusSeconds(1) ?: LocalTime.MIN
            // add the adjusted time to the date
            date.with(adjustedTime)
        }else{
            // If there are no existing entries on the same date, set the time to midnight
            date.with(LocalDateTime.MIN)
        }
    }


    fun deleteDiaryEntry(){
        //here just with dummy data with movie title as identifier
        LoggedMoviesDummy.removeIf { it.id == _diaryEntry.value?.id  }
    }
}