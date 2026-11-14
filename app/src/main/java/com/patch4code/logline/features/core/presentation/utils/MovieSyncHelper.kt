package com.patch4code.logline.features.core.presentation.utils

import android.util.Log
import com.patch4code.logline.api.RetrofitHelper
import com.patch4code.logline.api.TmdbApiService
import com.patch4code.logline.features.core.domain.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MovieSyncHelper - Utility to fetch missing details (like posters) from TMDB API for locally stored movies.
 *
 * @author Patch4Code
 */
object MovieSyncHelper {

    suspend fun syncMissingPosters(
        movies: List<Movie>,
        upsertMovie: suspend (Movie) -> Unit,
        onMovieUpdated: (Movie) -> Unit
    ) {
        val missingPosters = movies.filter { it.posterUrl.isNullOrBlank() }
        if (missingPosters.isEmpty()) return

        val tmdbApiService = RetrofitHelper.getInstance(TmdbCredentials.BASE_URL).create(TmdbApiService::class.java)

        withContext(Dispatchers.IO) {
            missingPosters.forEach { movie ->
                try {
                    val response = tmdbApiService.getMovieDetails(movie.id)
                    if (response.isSuccessful) {
                        response.body()?.let { details ->
                            if (!details.posterPath.isNullOrBlank()) {
                                val updatedMovie = movie.copy(posterUrl = details.posterPath)
                                upsertMovie(updatedMovie)
                                withContext(Dispatchers.Main) {
                                    onMovieUpdated(updatedMovie)
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.e("MovieSyncHelper", "Error syncing poster for movie ${movie.id}", e)
                }
            }
        }
    }
}
