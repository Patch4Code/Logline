package com.moritz.movieappuitest.features.diary.presentation.screen_diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moritz.movieappuitest.features.diary.domain.model.LoggedMovie
import com.moritz.movieappuitest.features.diary.domain.model.LoggedMoviesDummy

class DiaryEditElementViewModel: ViewModel() {

    private val _diaryEntry = MutableLiveData<LoggedMovie>()
    val diaryEntry: LiveData<LoggedMovie> get() = _diaryEntry

    fun setDiaryEntryToEdit(diaryEntryId: String?){
        _diaryEntry.value = LoggedMoviesDummy.find { it.id == diaryEntryId }
    }

    fun updatedDiaryEntry(rating: Int, watchDate: String){
        val movieTitle = _diaryEntry.value?.movie?.title

        //Here only updates dummy temporary (later probably use an id for identification)
        val updatedElement = LoggedMoviesDummy.find { it.movie.title == movieTitle } //later diary-id for find
        updatedElement?.let {
            it.date = watchDate
            it.rating = rating
        }
        _diaryEntry.value = updatedElement!!
    }

    fun deleteDiaryEntry(){
        //here just with dummy data with movie title as identifier
        LoggedMoviesDummy.removeIf { it.movie.id == _diaryEntry.value?.movie?.id }
    }
}