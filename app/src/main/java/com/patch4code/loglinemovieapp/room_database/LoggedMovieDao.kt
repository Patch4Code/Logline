package com.patch4code.loglinemovieapp.room_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.diary.domain.model.LoggedMovie
import com.patch4code.loglinemovieapp.features.diary.domain.model.MovieWithLoggedData
import com.patch4code.loglinemovieapp.room_database.utils.Queries
import java.time.LocalDateTime

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * LoggedMovieDao - Data Access Object (DAO) interface for
 * performing database operations related to LoggedMovie entities.
 *
 * @author Patch4Code
 */
@Dao
interface LoggedMovieDao {

    @Upsert
    suspend fun upsertLoggedMovie(loggedMovie: LoggedMovie)

    @Upsert
    suspend fun upsertMovie(movie: Movie)

    @Delete
    suspend fun deleteLoggedMovie(loggedMovie: LoggedMovie)


    //Movie Log actions---------------------------------------------------------------------------

    @Transaction
    suspend fun addNewMovieLog(loggedElement: LoggedMovie, movie: Movie){
        upsertLoggedMovie(loggedElement)
        upsertMovie(movie)
    }


    //Diary Edit Queries and Actions---------------------------------------------------------------

    @Query("SELECT * FROM LoggedMovie WHERE date BETWEEN :startOfDayMillis AND :endOfDayMillis")
    suspend fun getLoggedMoviesWithSameDate(startOfDayMillis: Long, endOfDayMillis: Long): List<LoggedMovie>

    @Transaction
    @Query("""
        SELECT log.*, m.*
        FROM LoggedMovie log
        INNER JOIN Movie m on log.movieId = m.id
        WHERE log.log_id = :logId
        LIMIT 1
        """
    )
    suspend fun getLoggedMovieById(logId: String?): MovieWithLoggedData?

    @Transaction
    suspend fun updateLoggedMovie(logId: String, rating: Int, watchDate: LocalDateTime, review: String) {
        val existingDiaryEntry = getLoggedMovieById(logId)?.loggedMovie

        if(existingDiaryEntry != null){
            existingDiaryEntry.rating = rating
            existingDiaryEntry.date = watchDate
            existingDiaryEntry.review = review
            upsertLoggedMovie(existingDiaryEntry)
        }
    }

    @Transaction
    suspend fun deleteDiaryEntry(movieWithLoggedData: MovieWithLoggedData){
        deleteLoggedMovie(movieWithLoggedData.loggedMovie)

        val movieId = movieWithLoggedData.movie.id
        if(countMovieReferences(movieId) < 1){
            deleteMovieById(movieId)
        }
    }

    @Query(Queries.COUNT_MOVIE_REFERENCES)
    suspend fun countMovieReferences(movieId: Int?): Int

    @Query("DELETE FROM Movie WHERE id = :movieId")
    suspend fun deleteMovieById(movieId: Int)


    // Diary Queries-------------------------------------------------------------------------------

    @Transaction
    @Query("""
        SELECT log.*, m.*
        FROM LoggedMovie log
        INNER JOIN Movie m on log.movieId = m.id
        ORDER BY log.date ASC
    """
    )
    suspend fun getLoggedMoviesOrderedByDateAsc() : List<MovieWithLoggedData>

    @Transaction
    @Query("""
        SELECT log.*, m.*
        FROM LoggedMovie log
        INNER JOIN Movie m on log.movieId = m.id
        ORDER BY log.date DESC
    """
    )
    suspend fun getLoggedMoviesOrderedByDateDesc() : List<MovieWithLoggedData>

    @Transaction
    @Query("""
        SELECT log.*, m.*
        FROM LoggedMovie log
        INNER JOIN Movie m on log.movieId = m.id
        ORDER BY log.rating ASC
    """
    )
    suspend fun getLoggedMoviesOrderedByRatingAsc() : List<MovieWithLoggedData>

    @Transaction
    @Query("""
        SELECT log.*, m.*
        FROM LoggedMovie log
        INNER JOIN Movie m on log.movieId = m.id
        ORDER BY log.rating DESC
    """
    )
    suspend fun getLoggedMoviesOrderedByRatingDesc() : List<MovieWithLoggedData>

    @Transaction
    @Query("""
        SELECT log.*, m.*
        FROM LoggedMovie log
        INNER JOIN Movie m on log.movieId = m.id
        ORDER BY m.title ASC
    """
    )
    suspend fun getLoggedMoviesOrderedByTitleAsc() : List<MovieWithLoggedData>

    @Transaction
    @Query("""
        SELECT log.*, m.*
        FROM LoggedMovie log
        INNER JOIN Movie m on log.movieId = m.id
        ORDER BY m.title DESC
    """
    )
    suspend fun getLoggedMoviesOrderedByTitleDesc() : List<MovieWithLoggedData>

    @Transaction
    @Query("""
        SELECT log.*, m.*
        FROM LoggedMovie log
        INNER JOIN Movie m on log.movieId = m.id
        ORDER BY m.releaseDate ASC
    """
    )
    suspend fun getLoggedMoviesOrderedByReleaseDateAsc() : List<MovieWithLoggedData>

    @Transaction
    @Query("""
        SELECT log.*, m.*
        FROM LoggedMovie log
        INNER JOIN Movie m on log.movieId = m.id
        ORDER BY m.releaseDate DESC
    """
    )
    suspend fun getLoggedMoviesOrderedByReleaseDateDesc() : List<MovieWithLoggedData>

    @Transaction
    @Query("""
        SELECT log.*, m.*
        FROM LoggedMovie log
        INNER JOIN Movie m on log.movieId = m.id
        ORDER BY m.popularity ASC
    """
    )
    suspend fun getLoggedMoviesOrderedByPopularityAsc(): List<MovieWithLoggedData>

    @Transaction
    @Query("""
        SELECT log.*, m.*
        FROM LoggedMovie log
        INNER JOIN Movie m on log.movieId = m.id
        ORDER BY m.popularity DESC
    """
    )
    suspend fun getLoggedMoviesOrderedByPopularityDesc(): List<MovieWithLoggedData>

    @Transaction
    @Query("""
        SELECT log.*, m.*
        FROM LoggedMovie log
        INNER JOIN Movie m on log.movieId = m.id
        ORDER BY m.voteAverage ASC
    """
    )
    suspend fun getLoggedMoviesOrderedByVoteAverageAsc(): List<MovieWithLoggedData>

    @Transaction
    @Query("""
        SELECT log.*, m.*
        FROM LoggedMovie log
        INNER JOIN Movie m on log.movieId = m.id
        ORDER BY m.voteAverage DESC
    """
    )
    suspend fun getLoggedMoviesOrderedByVoteAverageDesc(): List<MovieWithLoggedData>


    // Reviews Queries-----------------------------------------------------------------------------

    @Transaction
    @Query("""
        SELECT log.*, m.*
        FROM LoggedMovie log
        INNER JOIN Movie m on log.movieId = m.id
        WHERE log.review IS NOT NULL AND log.review != ''
        ORDER BY log.date ASC
    """
    )
    suspend fun getReviewsOrderedByDateAsc() : List<MovieWithLoggedData>

    @Transaction
    @Query("""
        SELECT log.*, m.*
        FROM LoggedMovie log
        INNER JOIN Movie m on log.movieId = m.id
        WHERE log.review IS NOT NULL AND log.review != ''
        ORDER BY log.date DESC
    """
    )
    suspend fun getReviewsOrderedByDateDesc() : List<MovieWithLoggedData>

    @Transaction
    @Query("""
        SELECT log.*, m.*
        FROM LoggedMovie log
        INNER JOIN Movie m on log.movieId = m.id
        WHERE log.review IS NOT NULL AND log.review != ''
        ORDER BY log.rating ASC
    """
    )
    suspend fun getReviewsOrderedByRatingAsc() : List<MovieWithLoggedData>

    @Transaction
    @Query("""
        SELECT log.*, m.*
        FROM LoggedMovie log
        INNER JOIN Movie m on log.movieId = m.id
        WHERE log.review IS NOT NULL AND log.review != ''
        ORDER BY log.rating DESC
    """
    )
    suspend fun getReviewsOrderedByRatingDesc() : List<MovieWithLoggedData>

    @Transaction
    @Query("""
        SELECT log.*, m.*
        FROM LoggedMovie log
        INNER JOIN Movie m on log.movieId = m.id
        WHERE log.review IS NOT NULL AND log.review != ''
        ORDER BY m.title ASC
    """
    )
    suspend fun getReviewsOrderedByTitleAsc() : List<MovieWithLoggedData>

    @Transaction
    @Query("""
        SELECT log.*, m.*
        FROM LoggedMovie log
        INNER JOIN Movie m on log.movieId = m.id
        WHERE log.review IS NOT NULL AND log.review != ''
        ORDER BY m.title DESC
    """
    )
    suspend fun getReviewsOrderedByTitleDesc() : List<MovieWithLoggedData>

    @Transaction
    @Query("""
        SELECT log.*, m.*
        FROM LoggedMovie log
        INNER JOIN Movie m on log.movieId = m.id
        WHERE log.review IS NOT NULL AND log.review != ''
        ORDER BY m.releaseDate ASC
    """
    )
    suspend fun getReviewsOrderedByReleaseDateAsc() : List<MovieWithLoggedData>

    @Transaction
    @Query("""
        SELECT log.*, m.*
        FROM LoggedMovie log
        INNER JOIN Movie m on log.movieId = m.id
        WHERE log.review IS NOT NULL AND log.review != ''
        ORDER BY m.releaseDate DESC
    """
    )
    suspend fun getReviewsOrderedByReleaseDateDesc() : List<MovieWithLoggedData>

    @Transaction
    @Query("""
        SELECT log.*, m.*
        FROM LoggedMovie log
        INNER JOIN Movie m on log.movieId = m.id
        WHERE log.review IS NOT NULL AND log.review != ''
        ORDER BY m.popularity ASC
    """
    )
    suspend fun getReviewsOrderedByPopularityAsc(): List<MovieWithLoggedData>

    @Transaction
    @Query("""
        SELECT log.*, m.*
        FROM LoggedMovie log
        INNER JOIN Movie m on log.movieId = m.id
        WHERE log.review IS NOT NULL AND log.review != ''
        ORDER BY m.popularity DESC
    """
    )
    suspend fun getReviewsOrderedByPopularityDesc(): List<MovieWithLoggedData>

    @Transaction
    @Query("""
        SELECT log.*, m.*
        FROM LoggedMovie log
        INNER JOIN Movie m on log.movieId = m.id
        WHERE log.review IS NOT NULL AND log.review != ''
        ORDER BY m.voteAverage ASC
    """
    )
    suspend fun getReviewsOrderedByVoteAverageAsc(): List<MovieWithLoggedData>

    @Transaction
    @Query("""
        SELECT log.*, m.*
        FROM LoggedMovie log
        INNER JOIN Movie m on log.movieId = m.id
        WHERE log.review IS NOT NULL AND log.review != ''
        ORDER BY m.voteAverage DESC
    """
    )
    suspend fun getReviewsOrderedByVoteAverageDesc(): List<MovieWithLoggedData>
}