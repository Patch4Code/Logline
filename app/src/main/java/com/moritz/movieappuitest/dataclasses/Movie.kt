package com.moritz.movieappuitest.dataclasses

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("title") val title: String = "N/A",
    @SerializedName("id") val id: Int = -1,
    @SerializedName("release_date") val releaseDate: String = "N/A-date",
    @SerializedName("poster_path") val posterUrl: String = "",
){
    //override hashCode-function for movie because release_date and poster_path can be null
    override fun hashCode(): Int { return title.hashCode() + id.hashCode() }
    //override equals-function because it is recommended to implement the equals() and hashCode() methods together
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Movie

        if (title != other.title) return false
        if (id != other.id) return false

        return true
    }
}

