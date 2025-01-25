package com.patch4code.logline.features.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * Movie - Represents movie data retrieved from TMDB
 *
 * @author Patch4Code
 */
@Entity
data class Movie(
    @PrimaryKey @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String = "N/A",
    @SerializedName("release_date") val releaseDate: String = "N/A-date",
    @SerializedName("poster_path") val posterUrl: String? = null,
    @SerializedName("genre_ids") val genreIds: List<Int> = emptyList(),
    @SerializedName("original_language") val originalLanguage: String = "N/A",
    @SerializedName("original_title") val originalTitle: String = "N/A",
    @SerializedName("popularity") val popularity: Double = 0.0,
    @SerializedName("vote_average") val voteAverage: Double = 0.0,
    val runtime: Int? = null
)