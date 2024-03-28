package com.patch4code.loglinemovieapp.room_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieUserData

@Dao
interface MovieUserDataDao {

    @Upsert
    suspend fun insertMovieUserData(movieUserData: MovieUserData)

    @Delete
    suspend fun deleteMovieUserData(movieUserData: MovieUserData)

    @Query("SELECT * FROM movieUserData")
    fun getMovieUserDataList() : List<MovieUserData>
}