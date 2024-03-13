package com.patch4code.loglinemovieapp.features.core.domain.model

data class MovieUserData(
    val movie: Movie? = null,
    var onWatchlist: Boolean = false,
    var rating: Int = -1
)

val userDataList = mutableListOf(
    MovieUserData(
        movie = Movie(title = "The Unbearable Weight of Massive Talent", id = 648579, releaseDate = "2023-01-01", posterUrl = "/yblAI3CPDGQtVU3ZafeprDAa2f6.jpg"),
        onWatchlist = true,
        rating = 10),
    MovieUserData(
        movie = Movie("The Banshees of Inisherin", 674324, "2202-01-22", "/51IhKxfTSmkhRhUJzrZSTf8vXzu.jpg"),
        onWatchlist = true,
        rating = 7),
    MovieUserData(
        movie = Movie("Limbo", 591222, "2021-01-01", "/7Hh2w1ei26DTJ3JWmW8qcGQUI1n.jpg"),
        onWatchlist = true,
        rating = 6),
    MovieUserData(
        movie = Movie("The Grand Budapest Hotel", 120467, "2014-01-01", "/cnYv3px6xOUryhCvasKO40v3fPD.jpg"),
        onWatchlist = true,
        rating = 8),
    MovieUserData(
        movie = Movie("Searching", 489999, "2018-01-01","/wbJQquzn9pYvgWQL5et9gmgRffQ.jpg"),
        onWatchlist = true,
        rating = 8),
    MovieUserData(
        movie = Movie("Saltburn", 930564, "2023-01-01","/wCMwBVQieh0YqIjJbsFINBQR6D3.jpg"),
        onWatchlist = true,
        rating = 6),
    MovieUserData(
        movie = Movie("Mad Max: Fury Road", 76341, "2015-01-01", "/aiKk3mj4pxg8gVzIVBiVT1aU3mh.jpg"),
        onWatchlist = true,
        rating = 9),

)