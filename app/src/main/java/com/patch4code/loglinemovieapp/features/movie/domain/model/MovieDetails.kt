package com.patch4code.loglinemovieapp.features.movie.domain.model

import com.google.gson.annotations.SerializedName

/**
 * APACHE LICENSE, VERSION 2.0 (https://www.apache.org/licenses/LICENSE-2.0)
 *
 * MovieDetails - Represents detailed information about a movie from TMDB.
 *
 * @author Patch4Code
 */
data class MovieDetails(
    @SerializedName("adult") val adult: Boolean = false,
    @SerializedName("backdrop_path") val backdropPath: String = "",
    @SerializedName("belongs_to_collection") val collection: Collection? = null,
    @SerializedName("budget") val budget: Int = 0,
    @SerializedName("genres") val genres: List<Genre> = emptyList(),
    @SerializedName("homepage") val homepage: String = "",
    @SerializedName("id") val id: Int = 0,
    @SerializedName("imdb_id") val imdbId: String = "",
    @SerializedName("original_language") val originalLanguage: String = "",
    @SerializedName("original_title") val originalTitle: String = "",
    @SerializedName("overview") val overview: String = "",
    @SerializedName("popularity") val popularity: Double = 0.0,
    @SerializedName("poster_path") val posterPath: String = "",
    @SerializedName("production_companies") val productionCompanies: List<ProductionCompany> = emptyList(),
    @SerializedName("production_countries") val productionCountries: List<ProductionCountry> = emptyList(),
    @SerializedName("release_date") val releaseDate: String = "",
    @SerializedName("revenue") val revenue: Long = 0,
    @SerializedName("runtime") val runtime: Int = 0,
    @SerializedName("spoken_languages") val spokenLanguages: List<SpokenLanguage> = emptyList(),
    @SerializedName("status") val status: String = "",
    @SerializedName("tagline") val tagline: String = "",
    @SerializedName("title") val title: String = "",
    @SerializedName("video") val video: Boolean = false,
    @SerializedName("vote_average") val voteAverage: Double = 0.0,
    @SerializedName("vote_count") val voteCount: Int = 0
)

// Collection - Represents a collection to which a movie belongs.
data class Collection(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("name") val name: String = "",
    @SerializedName("poster_path") val posterPath: String = "",
    @SerializedName("backdrop_path") val backdropPath: String = ""
)

// Genre - Represents a genre associated with a movie.
data class Genre(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("name") val name: String = ""
)

// ProductionCompany - Represents a production company involved in a movie.
data class ProductionCompany(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("logo_path") val logoPath: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("origin_country") val originCountry: String = ""
)

// ProductionCountry - Represents a production country for a movie.
data class ProductionCountry(
    @SerializedName("iso_3166_1") val iso31661: String = "",
    @SerializedName("name") val name: String = ""
)

// SpokenLanguage - Represents a spoken language in a movie.
data class SpokenLanguage(
    @SerializedName("english_name") val englishName: String = "",
    @SerializedName("iso_639_1") val iso6391: String = "",
    @SerializedName("name") val name: String = ""
)