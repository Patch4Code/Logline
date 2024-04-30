package com.patch4code.loglinemovieapp.features.profile.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * UserProfile - Represents a user profile.
 *
 * @author Patch4Code
 */
@Entity
data class UserProfile(
    var username: String = DEFAULT_USERNAME,
    var profileImagePath: String = "",
    var bannerImagePath: String = "",
    var bioText: String = "",
    var favouriteMovies: List<Movie> = EMPTY_MOVIE_LIST,
    @PrimaryKey
    val id:Int = 1
){
    companion object {
        const val DEFAULT_USERNAME = "Anonymous"
        val EMPTY_MOVIE_LIST = listOf(Movie(), Movie(), Movie(), Movie())
    }
}