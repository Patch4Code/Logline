package com.moritz.movieappuitest.dataclasses

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("title") val title: String = "N/A",
    @SerializedName("id") val id: Int = -1,
    @SerializedName("release_date") val releaseDate: String = "N/A-date",
    @SerializedName("poster_path") val posterUrl: String = "",
)


fun getWatchlistDummy(): List<Movie>{
    return listOf(
        Movie(title = "The Unbearable Weight of Massive Talent", id = 648579, releaseDate = "2023-01-01", posterUrl = "/yblAI3CPDGQtVU3ZafeprDAa2f6.jpg"),
        Movie("The Banshees of Inisherin", 674324, "2202-01-22", "/51IhKxfTSmkhRhUJzrZSTf8vXzu.jpg"),
        Movie("Limbo", 591222, "2021-01-01", "/7Hh2w1ei26DTJ3JWmW8qcGQUI1n.jpg"),
        Movie("The Grand Budapest Hotel", 120467, "2014-01-01", "/cnYv3px6xOUryhCvasKO40v3fPD.jpg"),
        Movie("Searching", 489999, "2018-01-01","/wbJQquzn9pYvgWQL5et9gmgRffQ.jpg"),
        Movie("Saltburn", 930564, "2023-01-01","/wCMwBVQieh0YqIjJbsFINBQR6D3.jpg"),
        Movie("Mad Max: Fury Road", 76341, "2015-01-01", "/aiKk3mj4pxg8gVzIVBiVT1aU3mh.jpg"),
        Movie("The Unbearable Weight of Massive Talent", 648579, "2023-01-01", "/yblAI3CPDGQtVU3ZafeprDAa2f6.jpg"),
        Movie("The Banshees of Inisherin", 674324, "2202-01-22", "/51IhKxfTSmkhRhUJzrZSTf8vXzu.jpg"),
        Movie("Limbo", 591222, "2021-01-01", "/7Hh2w1ei26DTJ3JWmW8qcGQUI1n.jpg"),
        Movie("The Grand Budapest Hotel", 120467, "2014-01-01", "/cnYv3px6xOUryhCvasKO40v3fPD.jpg"),
        Movie("Searching", 489999, "2018-01-01", "/wbJQquzn9pYvgWQL5et9gmgRffQ.jpg"),
        Movie("Saltburn", 930564, "2023-01-01", "/wCMwBVQieh0YqIjJbsFINBQR6D3.jpg"),
        Movie("Mad Max: Fury Road", 76341, "2015-01-01", "/aiKk3mj4pxg8gVzIVBiVT1aU3mh.jpg"),
        )
}

fun getRatedDummy(): List<Movie>{
    return listOf(
        Movie(title = "The Unbearable Weight of Massive Talent", id = 648579, releaseDate = "2023-01-01", posterUrl = "/yblAI3CPDGQtVU3ZafeprDAa2f6.jpg"),
        Movie(title = "The Unbearable Weight of Massive Talent", id = 648579, releaseDate = "2023-01-01", posterUrl = "/yblAI3CPDGQtVU3ZafeprDAa2f6.jpg"),
        Movie(title = "The Unbearable Weight of Massive Talent", id = 648579, releaseDate = "2023-01-01", posterUrl = "/yblAI3CPDGQtVU3ZafeprDAa2f6.jpg"),
        Movie(title = "The Unbearable Weight of Massive Talent", id = 648579, releaseDate = "2023-01-01", posterUrl = "/yblAI3CPDGQtVU3ZafeprDAa2f6.jpg"),
    )
}
