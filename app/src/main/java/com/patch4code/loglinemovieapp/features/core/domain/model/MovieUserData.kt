package com.patch4code.loglinemovieapp.features.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieUserData(
    val movie: Movie? = null,
    var onWatchlist: Boolean = false,
    var rating: Int = -1,
    @PrimaryKey(autoGenerate = false)
    val id: Int = movie?.id ?: -1
)