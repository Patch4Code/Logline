package com.moritz.movieappuitest.dataclasses

data class MovieList(
    val name: String,
    val isPublic: Boolean,
    val movies: List<Movie>
)
