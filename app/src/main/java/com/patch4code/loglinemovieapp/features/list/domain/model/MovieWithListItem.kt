package com.patch4code.loglinemovieapp.features.list.domain.model

import androidx.room.Embedded
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie

data class MovieWithListItem(
    @Embedded val movie: Movie,
    @Embedded val movieInList: MovieInList
)
