package com.patch4code.loglinemovieapp.features.core.domain.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("title") val title: String = "N/A",
    @SerializedName("id") val id: Int = -1,
    @SerializedName("release_date") val releaseDate: String = "N/A-date",
    @SerializedName("poster_path") val posterUrl: String = ""
)

