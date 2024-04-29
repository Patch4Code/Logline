package com.patch4code.loglinemovieapp.features.movie.domain.model

import com.google.gson.annotations.SerializedName
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie

/**
 * APACHE LICENSE, VERSION 2.0 (https://www.apache.org/licenses/LICENSE-2.0)
 *
 * MovieCollection - Represents a collection of movies (e.g. star wars movies) from TMDB
 *
 * @author Patch4Code
 */
data class MovieCollection(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("name") val name: String = "N/A",
    @SerializedName("overview") val overview: String = "N/A",
    @SerializedName("poster_path") val posterPath: String = "",
    @SerializedName("backdrop_path") val backdropPath: String = "",
    @SerializedName("parts") val movies: List<Movie> = emptyList(),
)
