package com.patch4code.loglinemovieapp.features.movie.presentation.screen_movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.diary.domain.model.LoggedMovie
import com.patch4code.loglinemovieapp.features.diary.domain.model.LoggedMoviesDummy
import com.patch4code.loglinemovieapp.room_database.MovieUserDataDao
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime

class MovieLogViewModel(private val userDataDao: MovieUserDataDao): ViewModel() {

    fun addMovieLog(movie: Movie, date: LocalDateTime, rating: Int, review: String){

        val adjustedDateTime = adjustedDateTime(date)
        val loggedElement = LoggedMovie(movie = movie, date = adjustedDateTime, rating = rating, review = review)

        LoggedMoviesDummy.add(loggedElement)
        updateRating(movie, rating)
        removeFromWatchlist(movie)
    }


    private fun updateRating(movie: Movie, rating: Int){
        viewModelScope.launch {
            userDataDao.updateOrInsertRating(movie, rating)
        }
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
            date.withHour(0).withMinute(0).withSecond(0).withNano(0)
        }
    }


    private fun removeFromWatchlist(movie: Movie){
        viewModelScope.launch {
            userDataDao.updateOrInsertOnWatchlist(movie, false)
        }
    }
}


class MovieLogViewModelFactory(private val dao: MovieUserDataDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieLogViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieLogViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}