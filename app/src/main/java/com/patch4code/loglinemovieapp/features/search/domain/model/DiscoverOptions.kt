package com.patch4code.loglinemovieapp.features.search.domain.model

data class DiscoverOptions(
    var sortBy: String = "popularity.desc",
    var genres: List<Int> = emptyList(),
    var primaryReleaseYear: Int? = null,
    var primaryReleaseDateGte: String? = null,
    var primaryReleaseDateLte: String? = null,
    val originCountry: String? = null,
    var originalLanguage: String? = null,
    val watchRegion: String? = null,
    var watchProviders: Map<Int, String> = emptyMap(),
    val withKeywords: String? = null,
    val withoutKeywords: String? = null,
    val voteAverageGte: Float? = 0f,
    val voteAverageLte: Float? = 10f,
    val voteCountGte: Float? = 100f,
    val people: String? = null,
    val companies: String? = null
) {
    fun getGenresAsString(separator: String = ","): String {
        return genres.joinToString(separator)
    }
    fun getWatchProvidersAsString(separator: String = "|"): String {
        return watchProviders.keys.joinToString(separator)
    }
    fun clearAllPrimaryReleases():DiscoverOptions  {
        return this.copy(
            primaryReleaseYear = null,
            primaryReleaseDateGte = null,
            primaryReleaseDateLte = null
        )
    }
    fun clearPrimaryReleaseYear():DiscoverOptions {
        return this.copy(
            primaryReleaseYear = null,
        )
    }
    fun clearPrimaryReleaseDates():DiscoverOptions {
        return this.copy(
            primaryReleaseDateGte = null,
            primaryReleaseDateLte = null
        )
    }
}
