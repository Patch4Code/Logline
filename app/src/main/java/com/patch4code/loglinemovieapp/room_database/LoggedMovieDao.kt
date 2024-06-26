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

    @Query("SELECT * FROM LoggedMovie ORDER BY date DESC")
    suspend fun getLoggedMovieListByDate() : List<LoggedMovie>

    @Query("SELECT * FROM LoggedMovie WHERE review IS NOT NULL AND review != '' ORDER BY date DESC")
    suspend fun getLoggedMovieWithReviewListByDate() : List<LoggedMovie>

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