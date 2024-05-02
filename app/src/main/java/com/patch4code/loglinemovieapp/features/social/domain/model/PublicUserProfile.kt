package com.patch4code.loglinemovieapp.features.social.domain.model

import com.patch4code.loglinemovieapp.features.core.domain.model.Movie

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * PublicUserProfile - Represents a public user profile.
 * Is separated from the local UserProfile class because here the userId is relevant for navigation.
 *
 * @author Patch4Code
 */
data class PublicUserProfile(
    val userId: String = "",
    var username: String = DEFAULT_USERNAME,
    var profileImagePath: String = "",
    var bannerImagePath: String = "",
    var bioText: String = "",
    var favouriteMovies: List<Movie>? = EMPTY_MOVIE_LIST,
){
    companion object {
        const val DEFAULT_USERNAME = "Anonymous"
        val EMPTY_MOVIE_LIST = listOf(Movie(), Movie(), Movie(), Movie())
    }
}
