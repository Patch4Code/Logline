package com.patch4code.loglinemovieapp.room_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieInList
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieWithListItem
import com.patch4code.loglinemovieapp.room_database.utils.Queries

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MovieInListDao - Data Access Object (DAO) interface for
 * performing database operations related to MovieInList entities.
 *
 * @author Patch4Code
 */
@Dao
interface MovieInListDao {

    @Upsert
    suspend fun upsertMovieInList(movieInList: MovieInList)

    @Upsert
    suspend fun upsertMovie(movie: Movie)

    @Delete
    suspend fun deleteMovieInList(movieInList: MovieInList)


    @Query("SELECT MAX(position) FROM MovieInList WHERE movieListId = :listId")
    suspend fun getHighestPositionInList(listId: String): Int?

    @Transaction
    suspend fun addMovieToList(movieInList: MovieInList, movie: Movie){
        upsertMovie(movie)
        upsertMovieInList(movieInList)
    }

    @Transaction
    suspend fun removeMovieFromList(listId: String, movieId: Int){
        val movieInListToDelete = getMovieInListByIds(listId, movieId)
        deleteMovieInList(movieInListToDelete)
        updatePositionsAfterRemoval(listId, movieInListToDelete.position)

        if(countMovieReferences(movieId) < 1){
            deleteMovieById(movieId)
        }
    }

    @Transaction
    suspend fun deleteAllMoviesFromList(listId: String){
        val movieIds = getAllMovieIdsInList(listId)

        deleteAllMoviesFromListDirect(listId)

        for (movieId in movieIds){
            if (countMovieReferences(movieId) < 1) {
                deleteMovieById(movieId)
            }
        }
    }

    @Query("SELECT * FROM movieInList WHERE movieListId = :listId AND movieId = :movieId LIMIT 1")
    suspend fun getMovieInListByIds(listId: String, movieId: Int): MovieInList

    @Query("UPDATE MovieInList SET position = position - 1 " +
            "WHERE movieListId = :listId AND position > :deletedPosition")
    suspend fun updatePositionsAfterRemoval(listId: String, deletedPosition: Int)

    @Query("SELECT movieId FROM MovieInList WHERE movieListId = :listId")
    suspend fun getAllMovieIdsInList(listId: String): List<Int>

    @Query("DELETE FROM MovieInList WHERE movieListId = :listId")
    suspend fun deleteAllMoviesFromListDirect(listId: String)

    @Query(Queries.COUNT_MOVIE_REFERENCES)
    suspend fun countMovieReferences(movieId: Int?): Int

    @Query("DELETE FROM Movie WHERE id = :movieId")
    suspend fun deleteMovieById(movieId: Int)



    //for addToListViewModel
    @Query("SELECT * FROM movieInList")
    suspend fun getAllMoviesInLists(): List<MovieInList>

    //for listsTable
    @Transaction
    @Query("""
        SELECT mil.*, m.* 
        FROM MovieInList mil
        INNER JOIN Movie m ON mil.movieId = m.id
    """)
    suspend fun getAllMovieWithListItems(): List<MovieWithListItem>


    // MovieInList Queries-------------------------------------------------------------------------

    @Transaction
    @Query("""
        SELECT mil.*, m.* 
        FROM MovieInList mil
        INNER JOIN Movie m ON mil.movieId = m.id
        WHERE mil.movieListId = :listId
        ORDER BY mil.position ASC
    """)
    suspend fun getMoviesInListOrderedByPositionAsc(listId: String): List<MovieWithListItem>

    @Query("""
        SELECT mil.*, m.* 
        FROM MovieInList mil
        INNER JOIN Movie m ON mil.movieId = m.id
        WHERE mil.movieListId = :listId
        ORDER BY m.title ASC
    """)
    suspend fun getMoviesInListOrderedByTitleAsc(listId: String): List<MovieWithListItem>

    @Query("""
        SELECT mil.*, m.* 
        FROM MovieInList mil
        INNER JOIN Movie m ON mil.movieId = m.id
        WHERE mil.movieListId = :listId
        ORDER BY m.title DESC
    """)
    suspend fun getMoviesInListOrderedByTitleDesc(listId: String): List<MovieWithListItem>

    @Query("""
        SELECT mil.*, m.* 
        FROM MovieInList mil
        INNER JOIN Movie m ON mil.movieId = m.id
        WHERE mil.movieListId = :listId
        ORDER BY m.releaseDate ASC
    """)
    suspend fun getMoviesInListOrderedByReleaseDateAsc(listId: String): List<MovieWithListItem>

    @Query("""
        SELECT mil.*, m.* 
        FROM MovieInList mil
        INNER JOIN Movie m ON mil.movieId = m.id
        WHERE mil.movieListId = :listId
        ORDER BY m.releaseDate DESC
    """)
    suspend fun getMoviesInListOrderedByReleaseDateDesc(listId: String): List<MovieWithListItem>

    @Query("""
        SELECT mil.*, m.* 
        FROM MovieInList mil
        INNER JOIN Movie m ON mil.movieId = m.id
        WHERE mil.movieListId = :listId
        ORDER BY mil.timeAdded ASC
    """)
    suspend fun getMoviesInListOrderedByTimeAddedAsc(listId: String): List<MovieWithListItem>

    @Query("""
        SELECT mil.*, m.* 
        FROM MovieInList mil
        INNER JOIN Movie m ON mil.movieId = m.id
        WHERE mil.movieListId = :listId
        ORDER BY mil.timeAdded DESC
    """)
    suspend fun getMoviesInListOrderedByTimeAddedDesc(listId: String): List<MovieWithListItem>

    @Query("""
        SELECT mil.*, m.* 
        FROM MovieInList mil
        INNER JOIN Movie m ON mil.movieId = m.id
        WHERE mil.movieListId = :listId
        ORDER BY m.popularity ASC
    """)
    suspend fun getMoviesInListOrderedByPopularityAsc(listId: String): List<MovieWithListItem>

    @Query("""
        SELECT mil.*, m.* 
        FROM MovieInList mil
        INNER JOIN Movie m ON mil.movieId = m.id
        WHERE mil.movieListId = :listId
        ORDER BY m.popularity DESC
    """)
    suspend fun getMoviesInListOrderedByPopularityDesc(listId: String): List<MovieWithListItem>

    @Query("""
        SELECT mil.*, m.* 
        FROM MovieInList mil
        INNER JOIN Movie m ON mil.movieId = m.id
        WHERE mil.movieListId = :listId
        ORDER BY m.voteAverage ASC
    """)
    suspend fun getMoviesInListOrderedByVoteAverageAsc(listId: String): List<MovieWithListItem>

    @Query("""
        SELECT mil.*, m.* 
        FROM MovieInList mil
        INNER JOIN Movie m ON mil.movieId = m.id
        WHERE mil.movieListId = :listId
        ORDER BY m.voteAverage DESC
    """)
    suspend fun getMoviesInListOrderedByVoteAverageDesc(listId: String): List<MovieWithListItem>
}