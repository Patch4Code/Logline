package com.patch4code.loglinemovieapp.features.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * APACHE LICENSE, VERSION 2.0 (https://www.apache.org/licenses/LICENSE-2.0)
 *
 * MovieUserData - Represents local user data for a specific movie (with rating and onWatchlist)
 *
 * @author Patch4Code
 */

@Entity
data class MovieUserData(
    val movie: Movie? = null,
    var onWatchlist: Boolean = false,
    var rating: Int = -1,
    @PrimaryKey(autoGenerate = false)
    val id: Int = movie?.id ?: -1
)