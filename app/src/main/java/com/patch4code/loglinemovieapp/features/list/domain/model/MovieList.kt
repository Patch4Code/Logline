package com.patch4code.loglinemovieapp.features.list.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie

@Entity
data class MovieList(
    var name: String,
    var isPublic: Boolean,
    var movies: List<Movie>,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
