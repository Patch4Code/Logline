package com.patch4code.loglinemovieapp.room_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieUserData

@Dao
interface MovieUserDataDao {

    @Upsert
    suspend fun upsertMovieUserData(movieUserData: MovieUserData)

    @Delete
    suspend fun deleteMovieUserData(movieUserData: MovieUserData)

    @Transaction
    suspend fun updateOrInsertRating(movie: Movie, rating: Int){
        val existingEntry = getMovieUserDataByMovieId(movie.id)
        if (existingEntry != null) {
            // Wenn ein Eintrag bereits vorhanden ist, aktualisieren Sie das Rating
            existingEntry.rating = rating
            upsertMovieUserData(existingEntry)
        } else {
            // Wenn kein Eintrag vorhanden ist, fügen Sie einen neuen Eintrag hinzu
            val newEntry = MovieUserData(movie = movie, rating = rating)
            upsertMovieUserData(newEntry)
        }
    }

    @Query("SELECT * FROM movieUserData WHERE id = :movieId LIMIT 1")
    suspend fun getMovieUserDataByMovieId(movieId: Int): MovieUserData?

    @Query("SELECT * FROM movieUserData")
    suspend fun getMovieUserDataList() : List<MovieUserData>
}