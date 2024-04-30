package com.patch4code.loglinemovieapp.features.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
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