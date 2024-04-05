package com.patch4code.loglinemovieapp.features.profile.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie

@Entity
data class UserProfile(
    var username: String = DEFAULT_USERNAME,
    var profileImagePath: String = "",
    var bannerImagePath: String = "",
    var bioText: String = "",
    val favouriteMovies: List<Movie> = EMPTY_MOVIE_LIST,
    @PrimaryKey
    val id:Int = 1
){
    companion object {
        const val DEFAULT_USERNAME = "Anonymous"
        val EMPTY_MOVIE_LIST = listOf(Movie(), Movie(), Movie(), Movie())
    }
}