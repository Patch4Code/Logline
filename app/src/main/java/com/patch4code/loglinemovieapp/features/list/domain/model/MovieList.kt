package com.patch4code.loglinemovieapp.features.list.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import java.util.UUID

/**
 * APACHE LICENSE, VERSION 2.0 (https://www.apache.org/licenses/LICENSE-2.0)
 *
 * MovieList - Represents a created list of movies
 *
 * @author Patch4Code
 */
@Entity
data class MovieList(
    var name: String,
    var isRanked: Boolean, //not used yet
    var movies: List<Movie>,
    @PrimaryKey
    val id: String = UUID.randomUUID().toString()
)
