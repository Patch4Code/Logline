package com.patch4code.loglinemovieapp.features.person_details.domain.model

import com.google.gson.annotations.SerializedName

data class PersonMovieCredits(
    @SerializedName("id") val id: Int = -1,
    @SerializedName("cast") val cast: List<MovieAsCastMember> = listOf(),
    @SerializedName("crew") val crew: List<MovieAsCrewMember> = listOf()
)

data class MovieAsCastMember(
    @SerializedName("title") val title: String = "N/A",
    @SerializedName("id") val id: Int = -1,
    @SerializedName("release_date") val releaseDate: String = "N/A-date",
    @SerializedName("poster_path") val posterUrl: String = "",
    @SerializedName("popularity") val popularity: Double = 0.0
)

data class MovieAsCrewMember(
    @SerializedName("title") val title: String = "N/A",
    @SerializedName("id") val id: Int = -1,
    @SerializedName("release_date") val releaseDate: String = "N/A-date",
    @SerializedName("poster_path") val posterUrl: String = "",
    @SerializedName("popularity") val popularity: Double = 0.0,
    @SerializedName("department") val department: String = "N/A"

)
