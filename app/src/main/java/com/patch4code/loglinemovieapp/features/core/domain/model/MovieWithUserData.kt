package com.patch4code.loglinemovieapp.features.core.domain.model

import androidx.room.Embedded

data class MovieWithUserData(
    @Embedded val movie: Movie,
    @Embedded val userData: MovieUserData
)
