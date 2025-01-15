package com.patch4code.loglinemovieapp.features.diary.domain.model

import androidx.room.Embedded
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie

data class MovieWithLog(
    @Embedded val movie: Movie,
    @Embedded val loggedMovie: LoggedMovie
)
