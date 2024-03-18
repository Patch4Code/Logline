package com.patch4code.loglinemovieapp.features.movie.presentation.screen_movie

import androidx.lifecycle.ViewModel
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieUserData
import com.patch4code.loglinemovieapp.features.core.domain.model.userDataList
import com.patch4code.loglinemovieapp.features.diary.domain.model.LoggedMovie
import com.patch4code.loglinemovieapp.features.diary.domain.model.LoggedMoviesDummy
import java.time.LocalDateTime
import java.time.LocalTime

class MovieLogViewModel: ViewModel() {

    fun addMovieLog(movie: Movie, date: LocalDateTime, rating: Int, review: String){

        val adjustedDateTime = adjustedDateTime(date)
        val loggedElement = LoggedMovie(movie = movie, date = adjustedDateTime, rating = rating, review = review)

        //add to LoggedMovies (Dummy)
        LoggedMoviesDummy.add(loggedElement)
        updateRating(movie, rating)
        removeFromWatchlist(movie)
    }


    private fun updateRating(movie: Movie, rating: Int){

        val movieUserData = userDataList.find { it.movie?.id == movie.id }

        if (movieUserData != null){
            movieUserData.rating = rating
        }else{
            val newMovieUserData = MovieUserData(
                movie  = movie,
                onWatchlist = false,
                rating = rating
            )
            userDataList.add(newMovieUserData)
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
        val movieUserData = userDataList.find { it.movie?.id == movie.id }
        if (movieUserData != null){
            movieUserData.onWatchlist = false
        }
    }
}