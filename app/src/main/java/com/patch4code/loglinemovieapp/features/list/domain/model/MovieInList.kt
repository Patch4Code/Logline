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
    /*
    val movieListId: String,
    val movieId: Int,
    val position: Int,
     */

    val movieListId: String,
    val position: Int,
    val movieId: Int,
    val title: String = "N/A",
    val releaseDate: String = "N/A-date",
    val posterUrl: String = "",
    val timeAdded: Long,
    val genreIds: List<Int> = emptyList(),
    val originalLanguage: String = "N/A",
    val originalTitle: String = "N/A",
    val popularity: Double = 0.0,
    val voteAverage: Double = 0.0,

)
