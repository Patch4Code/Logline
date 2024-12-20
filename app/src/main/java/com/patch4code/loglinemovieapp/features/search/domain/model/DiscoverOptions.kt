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
    val watchProviders: String? = null,
    val runtimeGte: Int? = null,
    val runtimeLte: Int? = null,
    val voteAverageGte: Float? = null,
    val voteAverageLte: Float? = null,
    val voteCountGte: Int? = 100,
    val people: String? = null,
    val companies: String? = null
) {
    fun getGenresAsString(separator: String = ","): String {
        return genres.joinToString(separator)
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
