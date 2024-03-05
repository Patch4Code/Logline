package com.moritz.movieappuitest.features.movie.domain.model

import com.google.gson.annotations.SerializedName
import com.moritz.movieappuitest.features.core.domain.model.Movie

data class MovieCollection(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("name") val name: String = "N/A",
    @SerializedName("overview") val overview: String = "N/A",
    @SerializedName("poster_path") val posterPath: String = "",
    @SerializedName("backdrop_path") val backdropPath: String = "",
    @SerializedName("parts") val movies: List<Movie> = emptyList(),
)
