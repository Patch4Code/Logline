package com.moritz.movieappuitest.dataclasses

data class MovieList(
    val name: String,
    val isPublic: Boolean,
    val movies: List<Movie>
)

val userMovieListsDummy = mutableListOf(
    MovieList(
        name = "My favorites List",
        isPublic = false,
        movies = mutableListOf(
            Movie(title = "The Unbearable Weight of Massive Talent", id = 648579, releaseDate = "2023-01-01", posterUrl = "/yblAI3CPDGQtVU3ZafeprDAa2f6.jpg"),
            Movie("Saltburn", 930564, "2023-01-01","/wCMwBVQieh0YqIjJbsFINBQR6D3.jpg"),
            Movie("The Grand Budapest Hotel", 120467, "2014-01-01", "/cnYv3px6xOUryhCvasKO40v3fPD.jpg")
        )
    ),
    MovieList(
        name = "Others",
        isPublic = true,
        movies = mutableListOf(
            Movie("The Banshees of Inisherin", 674324, "2202-01-22", "/51IhKxfTSmkhRhUJzrZSTf8vXzu.jpg"),
            Movie("Limbo", 591222, "2021-01-01", "/7Hh2w1ei26DTJ3JWmW8qcGQUI1n.jpg"),
            //Movie("The Grand Budapest Hotel", 120467, "2014-01-01", "/cnYv3px6xOUryhCvasKO40v3fPD.jpg")
        )
    ),
    MovieList(
        name = "More Movies I really like and even more of that",
        isPublic = false,
        movies = mutableListOf(
            Movie("Limbo", 591222, "2021-01-01", "/7Hh2w1ei26DTJ3JWmW8qcGQUI1n.jpg"),
        )
    ),
)
