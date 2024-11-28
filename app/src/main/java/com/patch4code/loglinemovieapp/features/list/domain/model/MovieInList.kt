package com.patch4code.loglinemovieapp.features.list.domain.model

import androidx.room.Entity

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MovieInList - Represents a Movie inside a List
 *
 * @author Patch4Code
 */
@Entity(primaryKeys = ["movieListId", "movieId"])
data class MovieInList(
    val movieListId: String,
    val position: Int,
    val movieId: Int,
    val title: String,
    val releaseDate: String,
    val posterUrl: String,
    val timeAdded: Long
)
