package com.patch4code.loglinemovieapp.features.profile.domain.model

import androidx.room.Embedded
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * UserProfileWithFavouriteMovies - Data class combining UserProfile entity with a List of movies.
 * This class is used to fetch data from the database, combining information stored in separate
 * database tables (Movie and UserProfile) into a single unified object.
 *
 * @author Patch4Code
 */
data class UserProfileWithFavouriteMovies(
    @Embedded val userProfile: UserProfile,
    val favouriteMovies: List<Movie?>
)