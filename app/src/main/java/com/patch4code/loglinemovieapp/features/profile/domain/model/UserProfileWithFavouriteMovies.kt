package com.patch4code.loglinemovieapp.features.profile.domain.model

import androidx.room.Embedded
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie

data class UserProfileWithFavouriteMovies(
    @Embedded val userProfile: UserProfile,
    val favouriteMovies: List<Movie?>
)
