package com.patch4code.loglinemovieapp.features.core.domain.model

import com.google.gson.annotations.SerializedName

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * Movie - Represents movie data retrieved from TMDB
 *
 * @author Patch4Code
 */

data class Movie(
    @SerializedName("title") val title: String = "N/A",
    @SerializedName("id") val id: Int = -1,
    @SerializedName("release_date") val releaseDate: String = "N/A-date",
    @SerializedName("poster_path") val posterUrl: String = ""
)

