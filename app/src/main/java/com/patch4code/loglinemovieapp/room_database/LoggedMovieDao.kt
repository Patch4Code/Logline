package com.patch4code.loglinemovieapp.room_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.patch4code.loglinemovieapp.features.diary.domain.model.LoggedMovie
import java.time.LocalDateTime

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * LoggedMovieDao - Data Access Object (DAO) interface for
 * performing database operations related to LoggedMovie entities.
 *
 * @author Patch4Code
 */
@Dao
interface LoggedMovieDao {

    @Upsert
    suspend fun upsertLoggedMovie(loggedMovie: LoggedMovie)

    @Delete
    suspend fun deleteLoggedMovie(loggedMovie: LoggedMovie)

    // Diary Queries
    @Query("SELECT * FROM LoggedMovie ORDER BY date ASC")
    suspend fun getLoggedMoviesOrderedByDateAsc() : List<LoggedMovie>
    @Query("SELECT * FROM LoggedMovie ORDER BY date DESC")
    suspend fun getLoggedMoviesOrderedByDateDesc() : List<LoggedMovie>
    @Query("SELECT * FROM LoggedMovie ORDER BY rating ASC")
    suspend fun getLoggedMoviesOrderedByRatingAsc() : List<LoggedMovie>
    @Query("SELECT * FROM LoggedMovie ORDER BY rating DESC")
    suspend fun getLoggedMoviesOrderedByRatingDesc() : List<LoggedMovie>
    @Query("SELECT * FROM LoggedMovie ORDER BY json_extract(movie, '\$.title') ASC")
    suspend fun getLoggedMoviesOrderedByTitleAsc() : List<LoggedMovie>
    @Query("SELECT * FROM LoggedMovie ORDER BY json_extract(movie, '\$.title') DESC")
    suspend fun getLoggedMoviesOrderedByTitleDesc() : List<LoggedMovie>
    @Query("SELECT * FROM LoggedMovie ORDER BY json_extract(movie, '\$.release_date') ASC")
    suspend fun getLoggedMoviesOrderedByReleaseDateAsc() : List<LoggedMovie>
    @Query("SELECT * FROM LoggedMovie ORDER BY json_extract(movie, '\$.release_date') DESC")
    suspend fun getLoggedMoviesOrderedByReleaseDateDesc() : List<LoggedMovie>
    @Query("SELECT * FROM LoggedMovie ORDER BY json_extract(movie, '\$.popularity') ASC")
    suspend fun getLoggedMoviesOrderedByPopularityAsc(): List<LoggedMovie>
    @Query("SELECT * FROM LoggedMovie ORDER BY json_extract(movie, '\$.popularity') DESC")
    suspend fun getLoggedMoviesOrderedByPopularityDesc(): List<LoggedMovie>
    @Query("SELECT * FROM LoggedMovie ORDER BY json_extract(movie, '\$.vote_average') ASC")
    suspend fun getLoggedMoviesOrderedByVoteAverageAsc(): List<LoggedMovie>
    @Query("SELECT * FROM LoggedMovie ORDER BY json_extract(movie, '\$.vote_average') DESC")
    suspend fun getLoggedMoviesOrderedByVoteAverageDesc(): List<LoggedMovie>

    // Reviews Queries
    @Query("SELECT * FROM LoggedMovie WHERE review IS NOT NULL AND review != '' ORDER BY date ASC")
    suspend fun getReviewsOrderedByDateAsc() : List<LoggedMovie>
    @Query("SELECT * FROM LoggedMovie WHERE review IS NOT NULL AND review != '' ORDER BY date DESC")
    suspend fun getReviewsOrderedByDateDesc() : List<LoggedMovie>
    @Query("SELECT * FROM LoggedMovie WHERE review IS NOT NULL AND review != '' ORDER BY rating ASC")
    suspend fun getReviewsOrderedByRatingAsc() : List<LoggedMovie>
    @Query("SELECT * FROM LoggedMovie WHERE review IS NOT NULL AND review != '' ORDER BY rating DESC")
    suspend fun getReviewsOrderedByRatingDesc() : List<LoggedMovie>
    @Query("SELECT * FROM LoggedMovie WHERE review IS NOT NULL AND review != '' ORDER BY json_extract(movie, '\$.title') ASC")
    suspend fun getReviewsOrderedByTitleAsc() : List<LoggedMovie>
    @Query("SELECT * FROM LoggedMovie WHERE review IS NOT NULL AND review != '' ORDER BY json_extract(movie, '\$.title') DESC")
    suspend fun getReviewsOrderedByTitleDesc() : List<LoggedMovie>
    @Query("SELECT * FROM LoggedMovie WHERE review IS NOT NULL AND review != '' ORDER BY json_extract(movie, '\$.release_date') ASC")
    suspend fun getReviewsOrderedByReleaseDateAsc() : List<LoggedMovie>
    @Query("SELECT * FROM LoggedMovie WHERE review IS NOT NULL AND review != '' ORDER BY json_extract(movie, '\$.release_date') DESC")
    suspend fun getReviewsOrderedByReleaseDateDesc() : List<LoggedMovie>
    @Query("SELECT * FROM LoggedMovie WHERE review IS NOT NULL AND review != '' ORDER BY json_extract(movie, '\$.popularity') ASC")
    suspend fun getReviewsOrderedByPopularityAsc(): List<LoggedMovie>
    @Query("SELECT * FROM LoggedMovie WHERE review IS NOT NULL AND review != '' ORDER BY json_extract(movie, '\$.popularity') DESC")
    suspend fun getReviewsOrderedByPopularityDesc(): List<LoggedMovie>
    @Query("SELECT * FROM LoggedMovie WHERE review IS NOT NULL AND review != '' ORDER BY json_extract(movie, '\$.vote_average') ASC")
    suspend fun getReviewsOrderedByVoteAverageAsc(): List<LoggedMovie>
    @Query("SELECT * FROM LoggedMovie WHERE review IS NOT NULL AND review != '' ORDER BY json_extract(movie, '\$.vote_average') DESC")
    suspend fun getReviewsOrderedByVoteAverageDesc(): List<LoggedMovie>


    @Query("SELECT * FROM LoggedMovie WHERE date BETWEEN :startOfDayMillis AND :endOfDayMillis")
    suspend fun getLoggedMoviesWithSameDate(startOfDayMillis: Long, endOfDayMillis: Long): List<LoggedMovie>

    @Query("SELECT * From LoggedMovie WHERE id = :logId LIMIT 1")
    suspend fun getLoggedMovieById(logId: String?): LoggedMovie?

    @Transaction
    suspend fun updateLoggedMovie(logId: String, rating: Int, watchDate: LocalDateTime, review: String) {
        val existingDiaryEntry = getLoggedMovieById(logId)

        if(existingDiaryEntry != null){
            existingDiaryEntry.rating = rating
            existingDiaryEntry.date = watchDate
            existingDiaryEntry.review = review
            upsertLoggedMovie(existingDiaryEntry)
        }
    }
}