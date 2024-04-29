package com.patch4code.loglinemovieapp.features.movie.domain.model

import com.google.gson.annotations.SerializedName

/**
 * APACHE LICENSE, VERSION 2.0 (https://www.apache.org/licenses/LICENSE-2.0)
 *
 * MovieVideos - Represents a collection of videos associated with a movie from TMDB.
 *
 * @author Patch4Code
 */
data class MovieVideos(
    @SerializedName("id") val id: Int = -1,
    @SerializedName("results") val videoList: List<MovieVideo> = emptyList()
)

// MovieVideo - Represents a video associated with a movie.
data class MovieVideo(
    @SerializedName("key") val key: String = "",
    @SerializedName("type") val type: String = "",
    @SerializedName("site") val site: String = ""
)
