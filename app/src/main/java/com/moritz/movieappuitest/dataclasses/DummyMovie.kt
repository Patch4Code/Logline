package com.moritz.movieappuitest.dataclasses

data class DummyMovie(
    val title: String = "Example Movie",
    val year: Int = 0,
    val posterUrl: String = "",
    val director: String = "",
    val length: Int = 0,
    val genre: List<String> = listOf(),
    val description: String = "",
    val rating: Int = 0
)


