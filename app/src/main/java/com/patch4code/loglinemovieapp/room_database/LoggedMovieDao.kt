package com.patch4code.loglinemovieapp.room_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.patch4code.loglinemovieapp.features.diary.domain.model.LoggedMovie

@Dao
interface LoggedMovieDao {

    @Upsert
    suspend fun upsertLoggedMovie(loggedMovie: LoggedMovie)

    @Delete
    suspend fun deleteLoggedMovie(loggedMovie: LoggedMovie)

    @Query("SELECT * FROM LoggedMovie ORDER BY date DESC")
    suspend fun getLoggedMovieListByDate() : List<LoggedMovie>

    @Query("SELECT * FROM LoggedMovie WHERE date BETWEEN :startOfDayMillis AND :endOfDayMillis")
    suspend fun getLoggedMoviesWithSameDate(startOfDayMillis: Long, endOfDayMillis: Long): List<LoggedMovie>
}