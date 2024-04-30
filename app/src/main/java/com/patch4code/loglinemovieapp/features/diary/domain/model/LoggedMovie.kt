package com.patch4code.loglinemovieapp.features.diary.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import java.time.LocalDateTime
import java.util.UUID

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * LoggedMovie - Data class representing a movie logged by a user
 *
 * @author Patch4Code
 */
@Entity
data class LoggedMovie(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val movie: Movie,
    var date: LocalDateTime,
    var rating: Int,
    var review: String = ""
)