package com.patch4code.logline.features.list.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MovieList - Represents a created list of movies
 *
 * @author Patch4Code
 */
@Entity
data class MovieList(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    var name: String,
    var isRanked: Boolean = false, //not used yet
    var timeCreated: Long,
    var timeUpdated: Long
)
