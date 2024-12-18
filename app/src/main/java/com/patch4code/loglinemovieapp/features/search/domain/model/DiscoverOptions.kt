package com.patch4code.loglinemovieapp.features.search.domain.model

data class DiscoverOptions(
    val sortBy: String = "popularity.desc",
    val genres: String? = null,
    val releaseDateGte: String? = null,
    val releaseDateLte: String? = null,
    val originalCountry: String? = null,
    val originalLanguage: String? = null,
    val watchRegion: String? = null,
    val watchProviders: String? = null,
    val runtimeGte: Int? = null,
    val runtimeLte: Int? = null,
    val voteAverageGte: Float? = null,
    val voteAverageLte: Float? = null,
    val voteCountGte: Int? = null,
    val people: String? = null,
    val companies: String? = null
)
