package com.moritz.movieappuitest.features.movie.domain.model

import com.google.gson.annotations.SerializedName

data class MovieCredits(
    @SerializedName("id") val id: Int = -1,
    @SerializedName("cast") val cast: List<Cast> = listOf(),
    @SerializedName("crew") val crew: List<Crew> = listOf()
)

data class Cast(
    @SerializedName("adult") val adult: Boolean = false,
    @SerializedName("gender") val gender: Int = 0,
    @SerializedName("id") val id: Int = -1,
    @SerializedName("known_for_department") val department: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("original_name") val originalName: String = "",
    @SerializedName("popularity") val popularity: Double = 0.0,
    @SerializedName("profile_path") val profilePath: String = "",
    @SerializedName("cast_id") val castId: Int = -1,
    @SerializedName("character") val character: String = "",
    @SerializedName("credit_id") val creditId: String = "",
    @SerializedName("order") val order: Int = 0,
)

data class Crew(
    @SerializedName("adult") val adult: Boolean = false,
    @SerializedName("gender") val gender: Int  = 0,
    @SerializedName("id") val id: Int  = -1,
    @SerializedName("known_for_department") val knownForDepartment: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("original_name") val originalName: String = "",
    @SerializedName("popularity") val popularity: Double = 0.0,
    @SerializedName("profile_path") val profilePath: String = "",
    @SerializedName("credit_id") val creditId: String = "",
    @SerializedName("department") val department: String = "",
    @SerializedName("job") val job: String  = "",
)