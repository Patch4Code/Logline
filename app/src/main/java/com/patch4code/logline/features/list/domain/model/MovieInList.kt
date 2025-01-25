package com.patch4code.logline.features.list.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.patch4code.logline.features.core.domain.model.Movie

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MovieInList - Represents a Movie inside a List
 *
 * @author Patch4Code
 */
@Entity(
    primaryKeys = ["movieListId", "movieId"],
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
data class MovieInList(
    val movieListId: String,
    val movieId: Int,
    val position: Int,
    val timeAdded: Long,
)
