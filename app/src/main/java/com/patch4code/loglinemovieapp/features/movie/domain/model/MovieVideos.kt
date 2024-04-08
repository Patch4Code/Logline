package com.patch4code.loglinemovieapp.features.movie.domain.model

import com.google.gson.annotations.SerializedName

data class MovieVideos(
    @SerializedName("id") val id: Int = -1,
    @SerializedName("results") val videoList: List<MovieVideo> = emptyList()
)

data class MovieVideo(
    @SerializedName("key") val key: String = "",
    @SerializedName("type") val type: String = "",
    @SerializedName("site") val site: String = ""
)
