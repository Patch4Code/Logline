package com.patch4code.loglinemovieapp.features.list.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import java.util.UUID

@Entity
data class MovieList(
    var name: String,
    var isRanked: Boolean, //not used yet
    var movies: List<Movie>,
    @PrimaryKey
    val id: String = UUID.randomUUID().toString()
)
