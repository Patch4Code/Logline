package com.moritz.movieappuitest.features.diary.presentation.screen_diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moritz.movieappuitest.features.diary.domain.model.LoggedMovie
import com.moritz.movieappuitest.features.diary.domain.model.LoggedMoviesDummy

class DiaryEditElementViewModel: ViewModel() {

    private val _diaryEntry = MutableLiveData<LoggedMovie>()
    val diaryEntry: LiveData<LoggedMovie> get() = _diaryEntry


    fun setDiaryEntryToEdit(diaryEntryToEdit: LoggedMovie){
        _diaryEntry.value = diaryEntryToEdit
    }

    fun updatedDiaryEntry(rating: Int, watchDate: String,){
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

    }
}