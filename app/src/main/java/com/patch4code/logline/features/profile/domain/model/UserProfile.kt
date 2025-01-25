package com.patch4code.logline.features.profile.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.patch4code.logline.features.core.domain.model.Movie

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * UserProfile - Represents a user profile.
 *
 * @author Patch4Code
 */
@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Movie::class,
            parentColumns = ["id"],
            childColumns = ["favouriteMovieId1"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = Movie::class,
            parentColumns = ["id"],
            childColumns = ["favouriteMovieId2"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = Movie::class,
            parentColumns = ["id"],
            childColumns = ["favouriteMovieId3"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = Movie::class,
            parentColumns = ["id"],
            childColumns = ["favouriteMovieId4"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [
        Index("favouriteMovieId1"),
        Index("favouriteMovieId2"),
        Index("favouriteMovieId3"),
        Index("favouriteMovieId4")
    ]
)
data class UserProfile(

    @PrimaryKey val id:Int = 1,
    var username: String = "Anonymous",
    var profileImagePath: String = "",
    var bannerImagePath: String = "",
    var bioText: String = "",
    var favouriteMovieId1: Int? = null,
    var favouriteMovieId2: Int? = null,
    var favouriteMovieId3: Int? = null,
    var favouriteMovieId4: Int? = null
)