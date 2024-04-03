package com.patch4code.loglinemovieapp.features.diary.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import java.time.LocalDateTime
import java.util.UUID

@Entity
data class LoggedMovie(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val movie: Movie,
    var date: LocalDateTime,
    var rating: Int,
    var review: String = ""
)