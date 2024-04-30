package com.patch4code.loglinemovieapp.features.movie_public_reviews.domain.model

import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import java.time.LocalDateTime

/**
 * APACHE LICENSE, VERSION 2.0 (https://www.apache.org/licenses/LICENSE-2.0)
 *
 * LoglineReview - Represents a review published by a user from Logline app.
 *
 * @author Patch4Code
 */
data class LoglineReview(
    val objectId: String,
    val userId: String,
    val authorName: String,
    val movie: Movie = Movie(),
    val content: String,
    val createdAt: LocalDateTime,
    val avatarPath: String,
    val rating: Int,
    val isProfilePublic: Boolean
)
