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

    @Transaction
    suspend fun deleteMovieListById(id: String){
        val listToDelete = getMovieListById(id)
        deleteMovieList(listToDelete)
    }

    @Query("SELECT * FROM movieList")
    suspend fun getMovieLists() : List<MovieList>

    @Query("SELECT * FROM movieList WHERE id = :listId LIMIT 1")
    suspend fun getMovieListById(listId: String): MovieList

    @Transaction
    suspend fun addMovieToList(listId: String, movie: Movie){
        val listToUpdate = getMovieListById(listId)

        val updatedMovies = listToUpdate.movies.toMutableList()
        updatedMovies.add(movie)

        listToUpdate.movies = updatedMovies
        upsertMovieList(listToUpdate)
    }

    @Transaction
    suspend fun removeMovieFromList(listId: String, movieId: Int){
        val listToUpdate = getMovieListById(listId)

        val updatedMovies = listToUpdate.movies.toMutableList()
        updatedMovies.removeIf { it.id == movieId }

        listToUpdate.movies = updatedMovies
        upsertMovieList(listToUpdate)
    }

    @Transaction
    suspend fun editListParameters(listId: String, newTitle: String, newIsPublicState: Boolean){
        val listToUpdate = getMovieListById(listId)

        listToUpdate.name = newTitle
        listToUpdate.isRanked = newIsPublicState
        upsertMovieList(listToUpdate)
    }
}