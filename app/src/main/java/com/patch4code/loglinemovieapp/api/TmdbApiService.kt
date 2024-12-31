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
import com.patch4code.loglinemovieapp.features.search.domain.model.MovieProvidersForCountry
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
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
        @Query("api_key") apiKey: String = TmdbCredentials.getApiKey()
    ): Response<MoviePage>

    @GET("movie/top_rated")
    suspend fun getTopRated(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = TmdbCredentials.getApiKey()
    ): Response<MoviePage>

    @GET("movie/upcoming")
    suspend fun getUpcoming(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = TmdbCredentials.getApiKey()
    ): Response<MoviePage>

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") searchQuery: String?,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = TmdbCredentials.getApiKey()
    ): Response<MoviePage>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int?,
        @Query("language") language: String = "en-US",
        @Query("api_key") apiKey: String = TmdbCredentials.getApiKey()
    ): Response<MovieCredits>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int?,
        @Query("language") language: String = "en-US",
        @Query("api_key") apiKey: String = TmdbCredentials.getApiKey()
    ): Response<MovieDetails>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int?,
        @Query("language") language: String = "en-US",
        @Query("api_key") apiKey: String = TmdbCredentials.getApiKey()
    ): Response<MovieVideos>

    @GET("movie/{movie_id}/watch/providers")
    suspend fun getWatchProviders(
        @Path("movie_id") movieId: Int?,
        @Query("api_key") apiKey: String = TmdbCredentials.getApiKey()
    ): Response<WatchProvides>

    @GET("collection/{collection_id}")
    suspend fun getMoviesFromCollection(
        @Path("collection_id") collectionId: Int?,
        @Query("language") language: String = "en-US",
        @Query("api_key") apiKey: String = TmdbCredentials.getApiKey()
    ): Response<MovieCollection>

    @GET("person/{person_id}")
    suspend fun getPersonDetails(
        @Path("person_id") personId: Int?,
        @Query("language") language: String = "en-US",
        @Query("api_key") apiKey: String = TmdbCredentials.getApiKey()
    ): Response<PersonDetails>

    @GET("person/{person_id}/movie_credits")
    suspend fun getPersonMovieCredits(
        @Path("person_id") personId: Int?,
        @Query("language") language: String = "en-US",
        @Query("api_key") apiKey: String = TmdbCredentials.getApiKey()
    ): Response<PersonMovieCredits>

    @GET("movie/{movie_id}/reviews")
    suspend fun getTmdbMovieReviews(
        @Path("movie_id") movieId: Int?,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = TmdbCredentials.getApiKey()
    ): Response<TmdbReviewResponse>

    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("language") language: String = "en-US",
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("with_genres") genres: String? = null,
        @Query("primary_release_year") primaryReleaseYear: Int? = null,
        @Query("primary_release_date.gte") primaryReleaseDateGte: String? = null,
        @Query("primary_release_date.lte") primaryReleaseDateLte: String? = null,
        @Query("with_origin_country") originCountry: String? = null,
        @Query("with_original_language") originalLanguage: String? = null,
        @Query("watch_region") watchRegion: String? = null,
        @Query("with_watch_providers") watchProviders: String? = null,
        @Query("with_keywords") withKeywords: String? = null,
        @Query("without_keywords") withoutKeywords: String? = null,
        @Query("vote_average.gte") voteAverageGte: Float? = null,
        @Query("vote_average.lte") voteAverageLte: Float? = null,
        @Query("vote_count.gte") voteCountGte: Float? = null,
        @Query("with_people") people: String? = null,
        @Query("with_companies") companies: String? = null,
        @Query("api_key") apiKey: String = TmdbCredentials.getApiKey(),
    ): Response<MoviePage>

    @GET("watch/providers/movie")
    suspend fun getMovieProvidersForCountry(
        @Query("language") language: String = "en-US",
        @Query("watch_region") watchRegion: String?,
        @Query("api_key") apiKey: String = TmdbCredentials.getApiKey()
    ): Response<MovieProvidersForCountry>
}