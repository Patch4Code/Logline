package com.patch4code.logline.room_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.patch4code.logline.features.core.domain.model.Movie

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MovieDao - Data Access Object (DAO) interface for
 * performing database operations related to Movie entities.
 *
 * @author Patch4Code
 */
@Dao
interface MovieDao {

    @Upsert
    suspend fun upsertMovie(movie: Movie)

    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Query("SELECT * FROM Movie WHERE id = :movieId")
    suspend fun getMovieById(movieId: Int): Movie?
}