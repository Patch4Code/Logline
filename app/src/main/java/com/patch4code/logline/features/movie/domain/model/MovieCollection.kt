package com.patch4code.logline.features.movie.domain.model

import com.google.gson.annotations.SerializedName
import com.patch4code.logline.features.core.domain.model.Movie

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
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
