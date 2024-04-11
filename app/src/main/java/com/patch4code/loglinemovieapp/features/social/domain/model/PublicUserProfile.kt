package com.patch4code.loglinemovieapp.features.social.domain.model

import com.patch4code.loglinemovieapp.features.core.domain.model.Movie

data class PublicUserProfile(
    val userId: String = "",
    var username: String = DEFAULT_USERNAME,
    var profileImagePath: String = "",
    var bannerImagePath: String = "",
    var bioText: String = "",
    var favouriteMovies: List<Movie> = EMPTY_MOVIE_LIST,
){
    companion object {
        const val DEFAULT_USERNAME = "Anonymous"
        val EMPTY_MOVIE_LIST = listOf(Movie(), Movie(), Movie(), Movie())
    }
}
