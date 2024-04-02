package com.patch4code.loglinemovieapp.room_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieList

@Dao
interface MovieListDao {

    @Upsert
    suspend fun upsertMovieList(movieList: MovieList)

    @Delete
    suspend fun deleteMovieList(movieList: MovieList)

    @Query("SELECT * FROM movieList")
    suspend fun getMovieLists() : List<MovieList>

}