package com.patch4code.loglinemovieapp.features.social.domain.model

import com.patch4code.loglinemovieapp.features.list.domain.model.MovieList
import java.util.Date

data class PublicList(
    val objectId: String,
    val userId: String,
    val publishedAt: Date,
    val authorName: String,
    val avatarPath: String,
    val movieList: MovieList,
)
