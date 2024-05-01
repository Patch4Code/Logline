package com.patch4code.loglinemovieapp.room_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieUserData

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MovieUserDataDao - Data Access Object (DAO) interface for
 * performing database operations related to MovieUserData entities.
 *
 * @author Patch4Code
 */
@Dao
interface MovieUserDataDao {

    @Upsert
    suspend fun upsertMovieUserData(movieUserData: MovieUserData)

    @Delete
    suspend fun deleteMovieUserData(movieUserData: MovieUserData)

    @Transaction
    suspend fun updateOrInsertRating(movie: Movie, rating: Int){
        val existingEntry = getMovieUserDataByMovieId(movie.id)

        // If an entry exists and has neither a rating nor is on the watchlist, delete the entry
        if(existingEntry != null && rating < 0 && !existingEntry.onWatchlist){
            deleteMovieUserData(existingEntry)
        }else{
            // Otherwise, update or insert the entry as usual
            val updatedEntry = existingEntry ?: MovieUserData(movie = movie)
            updatedEntry.rating = rating
            upsertMovieUserData(updatedEntry)
        }
    }

    @Transaction
    suspend fun updateOrInsertOnWatchlist(movie: Movie, onWatchlist: Boolean){
        val existingEntry = getMovieUserDataByMovieId(movie.id)

        // If an entry exists and is neither on the watchlist nor has a rating, delete the entry
        if (existingEntry != null && !onWatchlist && existingEntry.rating < 0) {
            deleteMovieUserData(existingEntry)
        } else {
            // Otherwise, update or insert the entry as usual
            val updatedEntry = existingEntry ?: MovieUserData(movie = movie)
            updatedEntry.onWatchlist = onWatchlist
            upsertMovieUserData(updatedEntry)
        }
    }

    @Query("SELECT * FROM movieUserData WHERE id = :movieId LIMIT 1")
    suspend fun getMovieUserDataByMovieId(movieId: Int): MovieUserData?

    @Query("SELECT * FROM movieUserData")
    suspend fun getMovieUserDataList() : List<MovieUserData>
}