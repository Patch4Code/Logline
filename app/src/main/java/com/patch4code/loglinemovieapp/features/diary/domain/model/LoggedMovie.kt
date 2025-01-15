package com.patch4code.loglinemovieapp.features.diary.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
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
data class LoggedMovie(
    @PrimaryKey
    @ColumnInfo(name = "log_id")
    val id: String = UUID.randomUUID().toString(),
    val movieId: Int,
    var date: LocalDateTime,
    var rating: Int,
    var review: String = ""
)