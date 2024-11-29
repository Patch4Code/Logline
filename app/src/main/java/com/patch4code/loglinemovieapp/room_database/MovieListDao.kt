package com.patch4code.loglinemovieapp.room_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieList

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MovieListDao - Data Access Object (DAO) interface for
 * performing database operations related to MovieList entities.
 *
 * @author Patch4Code
 */
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

    @Query("SELECT * FROM movieList WHERE id = :listId LIMIT 1")
    suspend fun getMovieListById(listId: String): MovieList

    @Query("UPDATE MovieList SET timeUpdated = :time WHERE id = :listId")
    suspend fun updateListTimeUpdated(listId: String, time: Long)

    @Transaction
    suspend fun editListParameters(listId: String, newTitle: String, newIsPublicState: Boolean){
        val listToUpdate = getMovieListById(listId)

        listToUpdate.name = newTitle
        listToUpdate.isRanked = newIsPublicState
        upsertMovieList(listToUpdate)
    }

    // MovieList Queries
    @Query("SELECT * FROM movieList ORDER BY timeUpdated DESC")
    suspend fun getMovieListsOrderedByTimeUpdated() : List<MovieList>
    @Query("SELECT * FROM movieList ORDER BY name ASC")
    suspend fun getMovieListsOrderedByNameAsc() : List<MovieList>
    @Query("SELECT * FROM movieList ORDER BY name DESC")
    suspend fun getMovieListsOrderedByNameDesc() : List<MovieList>
    @Query("SELECT * FROM movieList ORDER BY timeCreated ASC")
    suspend fun getMovieListsOrderedByTimeCreatedAsc() : List<MovieList>
    @Query("SELECT * FROM movieList ORDER BY timeCreated DESC")
    suspend fun getMovieListsOrderedByTimeCreatedDesc() : List<MovieList>
}