package com.patch4code.loglinemovieapp.features.movie_public_reviews.domain.model

import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import java.time.LocalDateTime

data class LoglineReview(
    val objectId: String,
    val userId: String,
    val authorName: String,
    val movie: Movie = Movie(),
    val content: String,
    val createdAt: LocalDateTime,
    val avatarPath: String,
    val rating: Int
)
