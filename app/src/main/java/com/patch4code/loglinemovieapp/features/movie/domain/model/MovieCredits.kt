package com.patch4code.loglinemovieapp.features.movie.domain.model

import com.google.gson.annotations.SerializedName

data class MovieCredits(
    @SerializedName("id") val id: Int = -1,
    @SerializedName("cast") val cast: List<Cast> = listOf(),
    @SerializedName("crew") val crew: List<Crew> = listOf()
)

data class Cast(
    @SerializedName("id") val id: Int = -1,
    @SerializedName("name") val name: String = "",
    @SerializedName("profile_path") val profilePath: String = "",
    @SerializedName("cast_id") val castId: Int = -1,
    @SerializedName("character") val character: String = ""
)

data class Crew(
    @SerializedName("id") val id: Int  = -1,
    @SerializedName("name") val name: String = "",
    @SerializedName("profile_path") val profilePath: String = "",
    @SerializedName("credit_id") val creditId: String = "",
    @SerializedName("job") val job: String  = "",
)