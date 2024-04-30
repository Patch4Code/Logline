package com.patch4code.loglinemovieapp.features.movie.presentation.screen_movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.diary.domain.model.LoggedMovie
import com.patch4code.loglinemovieapp.room_database.LoggedMovieDao
import com.patch4code.loglinemovieapp.room_database.MovieUserDataDao
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MovieLogViewModel - MovieLogViewModel responsible for managing the logging of a movie.
 *
 * @param loggedMovieDao The DAO for accessing logged movie data from the db.
 * @param userDataDao The DAO for accessing movie user data from the db.
 * @author Patch4Code
 */
class MovieLogViewModel(
    private val loggedMovieDao: LoggedMovieDao,
    private val userDataDao: MovieUserDataDao
): ViewModel() {

    // Adds a movie log entry with the provided details to the db.
    fun addMovieLog(movie: Movie, date: LocalDateTime, rating: Int, review: String){

        viewModelScope.launch {
            val adjustedDateTime = adjustedDateTime(date)
            //Log.e("MovieLogViewModel", "adjustedDateTime: $adjustedDateTime")
            val loggedElement = LoggedMovie(movie = movie, date = adjustedDateTime, rating = rating, review = review)

            loggedMovieDao.upsertLoggedMovie(loggedElement)
        }
        updateRating(movie, rating)
        removeFromWatchlist(movie)
    }

    // Updates the rating of a movie in the db.
    private fun updateRating(movie: Movie, rating: Int){
        viewModelScope.launch {
            userDataDao.updateOrInsertRating(movie, rating)
        }
    }

    // Adjusts the date and time of a log entry to avoid conflicts with existing entries on the same date.
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

    // Removes a movie from the user's watchlist by accessing the db.
    private fun removeFromWatchlist(movie: Movie){
        viewModelScope.launch {
            userDataDao.updateOrInsertOnWatchlist(movie, false)
        }
    }
}

// Factory-class for creating MovieLogViewModel instances to manage access to the database
class MovieLogViewModelFactory(private val loggedMovieDao: LoggedMovieDao, private val movieUserDataDao: MovieUserDataDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieLogViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieLogViewModel(loggedMovieDao, movieUserDataDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}