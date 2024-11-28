package com.patch4code.loglinemovieapp.room_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieInList

@Dao
interface MovieInListDao {

    @Upsert
    suspend fun upsertMovieInList(movieInList: MovieInList)

    @Delete
    suspend fun deleteMovieInList(movieInList: MovieInList)


    @Query("SELECT MAX(position) FROM MovieInList WHERE movieListId = :listId")
    suspend fun getHighestPositionInList(listId: String): Int?


    @Transaction
    suspend fun removeMovieFromList(listId: String, movieId: Int){
        val movieInListToDelete = getMovieInListByIds(listId, movieId)
        deleteMovieInList(movieInListToDelete)
        updatePositionsAfterRemoval(listId, movieInListToDelete.position)
    }
    @Query("UPDATE MovieInList SET position = position - 1 WHERE movieListId = :listId AND position > :deletedPosition")
    suspend fun updatePositionsAfterRemoval(listId: String, deletedPosition: Int)




    @Query("SELECT * FROM movieInList WHERE movieListId = :listId AND movieId = :movieId LIMIT 1")
    suspend fun getMovieInListByIds(listId: String, movieId: Int): MovieInList

    // MovieInList Queries
    @Query("SELECT * FROM movieInList WHERE movieListId = :listId ORDER BY position ASC")
    suspend fun getMoviesInListOrderedByPositionAsc(listId: String): List<MovieInList>
    @Query("SELECT * FROM movieInList WHERE movieListId = :listId ORDER BY position DESC")
    suspend fun getMoviesInListOrderedByPositionDesc(listId: String): List<MovieInList>
    @Query("SELECT * FROM movieInList WHERE movieListId = :listId ORDER BY title ASC")
    suspend fun getMoviesInListOrderedByTitleAsc(listId: String): List<MovieInList>
    @Query("SELECT * FROM movieInList WHERE movieListId = :listId ORDER BY title DESC")
    suspend fun getMoviesInListOrderedByTitleDesc(listId: String): List<MovieInList>
    @Query("SELECT * FROM movieInList WHERE movieListId = :listId ORDER BY releaseDate ASC")
    suspend fun getMoviesInListOrderedByReleaseDateAsc(listId: String): List<MovieInList>
    @Query("SELECT * FROM movieInList WHERE movieListId = :listId ORDER BY releaseDate DESC")
    suspend fun getMoviesInListOrderedByReleaseDateDesc(listId: String): List<MovieInList>
    @Query("SELECT * FROM movieInList WHERE movieListId = :listId ORDER BY timeAdded ASC")
    suspend fun getMoviesInListOrderedByTimeAddedAsc(listId: String): List<MovieInList>
    @Query("SELECT * FROM movieInList WHERE movieListId = :listId ORDER BY timeAdded DESC")
    suspend fun getMoviesInListOrderedByTimeAddedDesc(listId: String): List<MovieInList>
}