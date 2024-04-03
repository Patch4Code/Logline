package com.patch4code.loglinemovieapp.room_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieList

@Dao
interface MovieListDao {

    @Upsert
    suspend fun upsertMovieList(movieList: MovieList)

    @Delete
    suspend fun deleteMovieList(movieList: MovieList)

    @Query("SELECT * FROM movieList")
    suspend fun getMovieLists() : List<MovieList>

    @Query("SELECT * FROM movieList WHERE id = :listId")
    suspend fun getMovieListById(listId: Int): MovieList

    @Transaction
    suspend fun addMovieToList(listId: Int, movie: Movie){
        val listToUpdate = getMovieListById(listId)

        val updatedMovies = listToUpdate.movies.toMutableList()
        updatedMovies.add(movie)

        listToUpdate.movies = updatedMovies
        upsertMovieList(listToUpdate)
    }
}