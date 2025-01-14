package com.patch4code.loglinemovieapp.features.core.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MovieUserData - Represents local user data for a specific movie (with rating and onWatchlist)
 *
 * @author Patch4Code
 */

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Movie::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [Index("movieId")]
)
data class MovieUserData(
    @PrimaryKey val movieId: Int,
    var onWatchlist: Boolean = false,
    var rating: Int = -1,
    var addedToWatchlistTime: Long = 0,
    var addedToWatchedTime: Long = 0,
)