package com.moritz.movieappuitest.dataclasses

data class MovieUserData(
    val movie: Movie? = null,
    var onWatchlist: Boolean = false,
    var rating: Int = 0
)

val userDataList = mutableListOf(
    MovieUserData(
        movie = Movie(title = "The Unbearable Weight of Massive Talent", id = 648579, releaseDate = "2023-01-01", posterUrl = "/yblAI3CPDGQtVU3ZafeprDAa2f6.jpg"),
        onWatchlist = true,
        rating = 10)
)