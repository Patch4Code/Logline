package com.patch4code.loglinemovieapp.room_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieUserData
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieWithUserData
import com.patch4code.loglinemovieapp.room_database.utils.Queries

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

    @Upsert
    suspend fun upsertMovie(movie: Movie)

    @Delete
    suspend fun deleteMovieUserData(movieUserData: MovieUserData)



    //updateOrInsert MovieUserData ----------------------------------------------------------------

    @Transaction
    suspend fun updateOrInsertRating(movie: Movie, rating: Int){
        val existingEntry = getMovieUserDataByMovieId(movie.id)

        // If an entry exists and has neither a rating nor is on the watchlist, delete the entry
        if(existingEntry != null && rating < 0 && !existingEntry.onWatchlist){
            deleteMovieUserData(existingEntry)

            if(countMovieReferences(existingEntry.movieId) < 1){
                deleteMovieById(existingEntry.movieId)
            }
        }else{
            // update or insert movie
            upsertMovie(movie)

            // Otherwise, update or insert the entry as usual
            val updatedEntry = existingEntry
                ?: MovieUserData(movieId = movie.id, addedToWatchedTime = System.currentTimeMillis())
            updatedEntry.rating = rating
            if(updatedEntry.addedToWatchedTime == 0L)updatedEntry.addedToWatchedTime = System.currentTimeMillis()
            upsertMovieUserData(updatedEntry)
        }
    }

    @Transaction
    suspend fun updateOrInsertOnWatchlist(movie: Movie, onWatchlist: Boolean){
        val existingEntry = getMovieUserDataByMovieId(movie.id)

        // If an entry exists and is neither on the watchlist nor has a rating, delete the entry
        if (existingEntry != null && !onWatchlist && existingEntry.rating < 0) {
            deleteMovieUserData(existingEntry)

            if(countMovieReferences(existingEntry.movieId) < 1){
                deleteMovieById(existingEntry.movieId)
            }
        } else {
            // update or insert movie
            upsertMovie(movie)

            // Otherwise, update or insert the entry as usual
            val updatedEntry = existingEntry
                ?: MovieUserData(movieId = movie.id, addedToWatchlistTime = System.currentTimeMillis())
            updatedEntry.onWatchlist = onWatchlist
            updatedEntry.addedToWatchlistTime = System.currentTimeMillis()
            upsertMovieUserData(updatedEntry)
        }
    }

    @Query("SELECT * FROM movieUserData WHERE movieId = :movieId LIMIT 1")
    suspend fun getMovieUserDataByMovieId(movieId: Int): MovieUserData?

    @Query(Queries.COUNT_MOVIE_REFERENCES)
    suspend fun countMovieReferences(movieId: Int?): Int

    @Query("DELETE FROM Movie WHERE id = :movieId")
    suspend fun deleteMovieById(movieId: Int)

    // MyMovies Queries ----------------------------------------------------------------------------

    @Transaction
    @Query("""
        SELECT mud.*, m.* 
        FROM MovieUserData mud
        INNER JOIN Movie m ON mud.movieId = m.id
        WHERE mud.rating >= 0
        ORDER BY mud.addedToWatchedTime ASC
    """)
    suspend fun getWatchedMoviesOrderedByAddedAsc(): List<MovieWithUserData>

    @Transaction
    @Query("""
        SELECT mud.*, m.* 
        FROM MovieUserData mud
        INNER JOIN Movie m ON mud.movieId = m.id
        WHERE mud.rating >= 0
        ORDER BY mud.addedToWatchedTime DESC
    """)
    suspend fun getWatchedMoviesOrderedByAddedDesc(): List<MovieWithUserData>

    @Transaction
    @Query("""
        SELECT mud.*, m.* 
        FROM MovieUserData mud
        INNER JOIN Movie m ON mud.movieId = m.id
        WHERE mud.rating >= 0
        ORDER BY m.title ASC
    """)
    suspend fun getWatchedMoviesOrderedByTitleAsc(): List<MovieWithUserData>

    @Transaction
    @Query("""
        SELECT mud.*, m.* 
        FROM MovieUserData mud
        INNER JOIN Movie m ON mud.movieId = m.id
        WHERE mud.rating >= 0
        ORDER BY m.title DESC
    """)
    suspend fun getWatchedMoviesOrderedByTitleDesc(): List<MovieWithUserData>

    @Transaction
    @Query("""
        SELECT mud.*, m.* 
        FROM MovieUserData mud
        INNER JOIN Movie m ON mud.movieId = m.id
        WHERE mud.rating >= 0
        ORDER BY m.releaseDate ASC
    """)
    suspend fun getWatchedMoviesOrderedByReleaseDateAsc() : List<MovieWithUserData>

    @Transaction
    @Query("""
        SELECT mud.*, m.* 
        FROM MovieUserData mud
        INNER JOIN Movie m ON mud.movieId = m.id
        WHERE mud.rating >= 0
        ORDER BY m.releaseDate DESC
    """)
    suspend fun getWatchedMoviesOrderedByReleaseDateDesc(): List<MovieWithUserData>

    @Transaction
    @Query("""
        SELECT mud.*, m.* 
        FROM MovieUserData mud
        INNER JOIN Movie m ON mud.movieId = m.id
        WHERE mud.rating >= 0
        ORDER BY mud.rating ASC
    """)
    suspend fun getWatchedMoviesOrderedByRatingAsc(): List<MovieWithUserData>

    @Transaction
    @Query("""
        SELECT mud.*, m.* 
        FROM MovieUserData mud
        INNER JOIN Movie m ON mud.movieId = m.id
        WHERE mud.rating >= 0
        ORDER BY mud.rating DESC
    """)
    suspend fun getWatchedMoviesOrderedByRatingDesc(): List<MovieWithUserData>

    @Transaction
    @Query("""
        SELECT mud.*, m.* 
        FROM MovieUserData mud
        INNER JOIN Movie m ON mud.movieId = m.id
        WHERE mud.rating >= 0
        ORDER BY m.popularity ASC
    """)
    suspend fun getWatchedMoviesOrderedByPopularityAsc(): List<MovieWithUserData>

    @Transaction
    @Query("""
        SELECT mud.*, m.* 
        FROM MovieUserData mud
        INNER JOIN Movie m ON mud.movieId = m.id
        WHERE mud.rating >= 0
        ORDER BY m.popularity DESC
    """)
    suspend fun getWatchedMoviesOrderedByPopularityDesc(): List<MovieWithUserData>

    @Transaction
    @Query("""
        SELECT mud.*, m.* 
        FROM MovieUserData mud
        INNER JOIN Movie m ON mud.movieId = m.id
        WHERE mud.rating >= 0
        ORDER BY m.voteAverage ASC
    """)
    suspend fun getWatchedMoviesOrderedByVoteAverageAsc(): List<MovieWithUserData>

    @Transaction
    @Query("""
        SELECT mud.*, m.* 
        FROM MovieUserData mud
        INNER JOIN Movie m ON mud.movieId = m.id
        WHERE mud.rating >= 0
        ORDER BY m.voteAverage DESC
    """)
    suspend fun getWatchedMoviesOrderedByVoteAverageDesc(): List<MovieWithUserData>



    // Watchlist Queries----------------------------------------------------------------------------

    @Transaction
    @Query("""
        SELECT mud.*, m.* 
        FROM MovieUserData mud
        INNER JOIN Movie m ON mud.movieId = m.id
        WHERE mud.onWatchlist = 1
        ORDER BY mud.addedToWatchlistTime ASC
    """)
    suspend fun getWatchlistItemsOrderedByAddedAsc(): List<MovieWithUserData>

    @Transaction
    @Query("""
        SELECT mud.*, m.* 
        FROM MovieUserData mud
        INNER JOIN Movie m ON mud.movieId = m.id
        WHERE mud.onWatchlist = 1
        ORDER BY mud.addedToWatchlistTime DESC
    """)
    suspend fun getWatchlistItemsOrderedByAddedDesc(): List<MovieWithUserData>

    @Transaction
    @Query("""
        SELECT mud.*, m.* 
        FROM MovieUserData mud
        INNER JOIN Movie m ON mud.movieId = m.id
        WHERE mud.onWatchlist = 1
        ORDER BY m.title ASC
    """)
    suspend fun getWatchlistItemsOrderedByTitleAsc(): List<MovieWithUserData>

    @Transaction
    @Query("""
        SELECT mud.*, m.* 
        FROM MovieUserData mud
        INNER JOIN Movie m ON mud.movieId = m.id
        WHERE mud.onWatchlist = 1
        ORDER BY m.title DESC
    """)
    suspend fun getWatchlistItemsOrderedByTitleDesc(): List<MovieWithUserData>

    @Transaction
    @Query("""
        SELECT mud.*, m.* 
        FROM MovieUserData mud
        INNER JOIN Movie m ON mud.movieId = m.id
        WHERE mud.onWatchlist = 1
        ORDER BY m.releaseDate ASC
    """)
    suspend fun getWatchlistItemsOrderedByReleaseDateAsc(): List<MovieWithUserData>

    @Transaction
    @Query("""
        SELECT mud.*, m.* 
        FROM MovieUserData mud
        INNER JOIN Movie m ON mud.movieId = m.id
        WHERE mud.onWatchlist = 1
        ORDER BY m.releaseDate DESC
    """)
    suspend fun getWatchlistItemsOrderedByReleaseDateDesc(): List<MovieWithUserData>

    @Transaction
    @Query("""
        SELECT mud.*, m.* 
        FROM MovieUserData mud
        INNER JOIN Movie m ON mud.movieId = m.id
        WHERE mud.onWatchlist = 1
        ORDER BY m.popularity ASC
    """)
    suspend fun getWatchlistItemsOrderedByPopularityAsc(): List<MovieWithUserData>

    @Transaction
    @Query("""
        SELECT mud.*, m.* 
        FROM MovieUserData mud
        INNER JOIN Movie m ON mud.movieId = m.id
        WHERE mud.onWatchlist = 1
        ORDER BY m.popularity DESC
    """)
    suspend fun getWatchlistItemsOrderedByPopularityDesc(): List<MovieWithUserData>

    @Transaction
    @Query("""
        SELECT mud.*, m.* 
        FROM MovieUserData mud
        INNER JOIN Movie m ON mud.movieId = m.id
        WHERE mud.onWatchlist = 1
        ORDER BY m.voteAverage ASC
    """)
    suspend fun getWatchlistItemsOrderedByVoteAverageAsc(): List<MovieWithUserData>

    @Transaction
    @Query("""
        SELECT mud.*, m.* 
        FROM MovieUserData mud
        INNER JOIN Movie m ON mud.movieId = m.id
        WHERE mud.onWatchlist = 1
        ORDER BY m.voteAverage DESC
    """)
    suspend fun getWatchlistItemsOrderedByVoteAverageDesc(): List<MovieWithUserData>
}