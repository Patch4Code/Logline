package com.moritz.movieappuitest.dataclasses

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("title") val title: String,
    @SerializedName("id") val id: Int,

    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("genre_ids") val genreIds: List<Int>,

    @SerializedName("poster_path") val posterPath: String,

    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("overview") val overview: String
)
