package com.patch4code.loglinemovieapp.api

import com.patch4code.loglinemovieapp.features.core.domain.model.MoviePage
import com.patch4code.loglinemovieapp.features.core.presentation.utils.TmdbCredentials
import com.patch4code.loglinemovieapp.features.movie.domain.model.MovieCollection
import com.patch4code.loglinemovieapp.features.movie.domain.model.MovieCredits
import com.patch4code.loglinemovieapp.features.movie.domain.model.MovieDetails
import com.patch4code.loglinemovieapp.features.movie.domain.model.MovieVideos
import com.patch4code.loglinemovieapp.features.movie.domain.model.WatchProvides
import com.patch4code.loglinemovieapp.features.movie_public_reviews.domain.model.TmdbReviewResponse
import com.patch4code.loglinemovieapp.features.person_details.domain.model.PersonDetails
import com.patch4code.loglinemovieapp.features.person_details.domain.model.PersonMovieCredits
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * APACHE LICENSE, VERSION 2.0 (https://www.apache.org/licenses/LICENSE-2.0)
 *
 * TmdbApiService - Interface defining endpoints to interact with The Movie Database (TMDB) API.
 * These endpoints, which are specific URLs provided by the API, offer functionality to retrieve various movie-related data.

 * @author Patch4Code
 */

interface TmdbApiService {
    @GET("discover/movie")
    suspend fun getPopularMovies(
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("api_key") apiKey: String = TmdbCredentials.API_KEY
    ): Response<MoviePage>

    @GET("movie/top_rated")
    suspend fun getTopRated(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = TmdbCredentials.API_KEY
    ): Response<MoviePage>

    @GET("movie/upcoming")
    suspend fun getUpcoming(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = TmdbCredentials.API_KEY
    ): Response<MoviePage>

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") searchQuery: String?,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = TmdbCredentials.API_KEY
    ): Response<MoviePage>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int?,
        @Query("language") language: String = "en-US",
        @Query("api_key") apiKey: String = TmdbCredentials.API_KEY
    ): Response<MovieCredits>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int?,
        @Query("language") language: String = "en-US",
        @Query("api_key") apiKey: String = TmdbCredentials.API_KEY
    ): Response<MovieDetails>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int?,
        @Query("language") language: String = "en-US",
        @Query("api_key") apiKey: String = TmdbCredentials.API_KEY
    ): Response<MovieVideos>

    @GET("movie/{movie_id}/watch/providers")
    suspend fun getWatchProviders(
        @Path("movie_id") movieId: Int?,
        @Query("api_key") apiKey: String = TmdbCredentials.API_KEY
    ): Response<WatchProvides>

    @GET("collection/{collection_id}")
    suspend fun getMoviesFromCollection(
        @Path("collection_id") collectionId: Int?,
        @Query("language") language: String = "en-US",
        @Query("api_key") apiKey: String = TmdbCredentials.API_KEY
    ): Response<MovieCollection>

    @GET("person/{person_id}")
    suspend fun getPersonDetails(
        @Path("person_id") personId: Int?,
        @Query("language") language: String = "en-US",
        @Query("api_key") apiKey: String = TmdbCredentials.API_KEY
    ): Response<PersonDetails>

    @GET("person/{person_id}/movie_credits")
    suspend fun getPersonMovieCredits(
        @Path("person_id") personId: Int?,
        @Query("language") language: String = "en-US",
        @Query("api_key") apiKey: String = TmdbCredentials.API_KEY
    ): Response<PersonMovieCredits>

    @GET("movie/{movie_id}/reviews")
    suspend fun getTmdbMovieReviews(
        @Path("movie_id") movieId: Int?,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = TmdbCredentials.API_KEY
    ): Response<TmdbReviewResponse>
}