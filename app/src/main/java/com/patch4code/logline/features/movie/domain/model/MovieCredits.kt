package com.patch4code.logline.features.movie.domain.model

import com.google.gson.annotations.SerializedName

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MovieCredits - Represents the credits for a movie, including cast and crew members from TMDB.
 *
 * @author Patch4Code
 */
data class MovieCredits(
    @SerializedName("id") val id: Int = -1,
    @SerializedName("cast") val cast: List<Cast> = listOf(),
    @SerializedName("crew") val crew: List<Crew> = listOf()
)

// Represents a cast member of a movie from TMDB
data class Cast(
    @SerializedName("id") val id: Int = -1,
    @SerializedName("name") val name: String = "",
    @SerializedName("profile_path") val profilePath: String = "",
    @SerializedName("cast_id") val castId: Int = -1,
    @SerializedName("character") val character: String = ""
)

// Represents a crew member of a movie from TMDB
data class Crew(
    @SerializedName("id") val id: Int  = -1,
    @SerializedName("name") val name: String = "",
    @SerializedName("profile_path") val profilePath: String = "",
    @SerializedName("credit_id") val creditId: String = "",
    @SerializedName("job") val job: String  = "",
)