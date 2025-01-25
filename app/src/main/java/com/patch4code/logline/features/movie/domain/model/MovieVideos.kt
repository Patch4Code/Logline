package com.patch4code.logline.features.movie.domain.model

import com.google.gson.annotations.SerializedName

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
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
