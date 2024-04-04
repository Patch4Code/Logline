package com.patch4code.loglinemovieapp.features.profile.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie

@Entity
data class UserProfile(
    var username: String = DEFAULT_USERNAME,
    val profileImagePath: String = DEFAULT_PROFILE_IMAGE_PATH,
    val bannerImagePath: String = DEFAULT_BANNER_IMAGE_PATH,
    var bioText: String = "",
    val favouriteMovies: List<Movie> = EMPTY_MOVIE_LIST,
    @PrimaryKey
    val id:Int = 1
){
    companion object {
        const val DEFAULT_USERNAME = "Anonymous"
        const val DEFAULT_PROFILE_IMAGE_PATH = "default_profile_image"
        const val DEFAULT_BANNER_IMAGE_PATH = "default_banner_image"
        val EMPTY_MOVIE_LIST = listOf(Movie(), Movie(), Movie(), Movie())
    }
}