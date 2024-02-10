package com.moritz.movieappuitest.dataclasses

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("title") val title: String = "N/A",
    @SerializedName("id") val id: Int = -1,

    @SerializedName("release_date") val releaseDate: String = "N/A-date",
    @SerializedName("genre_ids") val genreIds: List<Int> = emptyList(),

    @SerializedName("poster_path") val posterUrl: String = "",

    @SerializedName("vote_average") val voteAverage: Float = 0.0f,
    @SerializedName("overview") val description: String = "N/A"
)


fun getWatchlistDummy(): List<Movie>{
    return listOf(
        Movie("The Unbearable Weight of Massive Talent", 1, "2023-01-01",mutableListOf(1, 2, 3), "/yblAI3CPDGQtVU3ZafeprDAa2f6.jpg",3.2f,"bla"),
        Movie("The Banshees of Inisherin", 1, "2202-01-22",mutableListOf(1, 2, 3), "/51IhKxfTSmkhRhUJzrZSTf8vXzu.jpg",3.2f,"bla"),
        Movie("Limbo", 1, "2021-01-01",mutableListOf(1, 2, 3), "/7Hh2w1ei26DTJ3JWmW8qcGQUI1n.jpg",3.2f,"bla"),
        Movie("The Grand Budapest Hotel", 1, "2014-01-01",mutableListOf(1, 2, 3), "/cnYv3px6xOUryhCvasKO40v3fPD.jpg",3.2f,"bla"),
        Movie("Searching", 1, "2018-01-01",mutableListOf(1, 2, 3), "/wbJQquzn9pYvgWQL5et9gmgRffQ.jpg",3.2f,"bla"),
        Movie("Saltburn", 1, "2023-01-01",mutableListOf(1, 2, 3), "/wCMwBVQieh0YqIjJbsFINBQR6D3.jpg",3.2f,"bla"),
        Movie("Mad Max: Fury Road", 1, "2015-01-01",mutableListOf(1, 2, 3), "/aiKk3mj4pxg8gVzIVBiVT1aU3mh.jpg",3.2f,"bla"),
        Movie("The Unbearable Weight of Massive Talent", 1, "2023-01-01",mutableListOf(1, 2, 3), "/yblAI3CPDGQtVU3ZafeprDAa2f6.jpg",3.2f,"bla"),
        Movie("The Banshees of Inisherin", 1, "2202-01-22",mutableListOf(1, 2, 3), "/51IhKxfTSmkhRhUJzrZSTf8vXzu.jpg",3.2f,"bla"),
        Movie("Limbo", 1, "2021-01-01",mutableListOf(1, 2, 3), "/7Hh2w1ei26DTJ3JWmW8qcGQUI1n.jpg",3.2f,"bla"),
        Movie("The Grand Budapest Hotel", 1, "2014-01-01",mutableListOf(1, 2, 3), "/cnYv3px6xOUryhCvasKO40v3fPD.jpg",3.2f,"bla"),
        Movie("Searching", 1, "2018-01-01",mutableListOf(1, 2, 3), "/wbJQquzn9pYvgWQL5et9gmgRffQ.jpg",3.2f,"bla"),
        Movie("Saltburn", 1, "2023-01-01",mutableListOf(1, 2, 3), "/wCMwBVQieh0YqIjJbsFINBQR6D3.jpg",3.2f,"bla"),
        Movie("Mad Max: Fury Road", 1, "2015-01-01",mutableListOf(1, 2, 3), "/aiKk3mj4pxg8gVzIVBiVT1aU3mh.jpg",3.2f,"bla"),

        )
}

fun getRatedDummy(): List<Movie>{
    return listOf(
        Movie("The Unbearable Weight of Massive Talent", 1, "2023-01-01",mutableListOf(1, 2, 3), "/yblAI3CPDGQtVU3ZafeprDAa2f6.jpg",3.2f,"bla"),
        Movie("The Unbearable Weight of Massive Talent", 1, "2023-01-01",mutableListOf(1, 2, 3), "/yblAI3CPDGQtVU3ZafeprDAa2f6.jpg",3.2f,"bla"),
        Movie("The Unbearable Weight of Massive Talent", 1, "2023-01-01",mutableListOf(1, 2, 3), "/yblAI3CPDGQtVU3ZafeprDAa2f6.jpg",3.2f,"bla"),
        Movie("The Unbearable Weight of Massive Talent", 1, "2023-01-01",mutableListOf(1, 2, 3), "/yblAI3CPDGQtVU3ZafeprDAa2f6.jpg",3.2f,"bla"),
    )
}
