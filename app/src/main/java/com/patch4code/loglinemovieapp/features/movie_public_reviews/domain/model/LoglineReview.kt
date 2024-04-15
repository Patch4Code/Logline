package com.patch4code.loglinemovieapp.features.movie_public_reviews.domain.model

import java.time.LocalDateTime

data class LoglineReview(
    val authorName: String,
    val userId: String,
    val content: String,
    val createdAt: LocalDateTime,
    val avatarPath: String,
    val rating: Int
)
