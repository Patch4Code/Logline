package com.patch4code.logline.features.movie_public_reviews.domain.model

import com.google.gson.annotations.SerializedName

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * TmdbReviewResponse - Represents the response structure for reviews from TMDB api.
 *
 * @author Patch4Code
 */
data class TmdbReviewResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<TmdbReview>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

// TmdbReview - Represents a review retrieved from TMDB api.
data class TmdbReview(
    @SerializedName("author") val author: String,
    @SerializedName("author_details") val tmdbAuthorDetails: TmdbAuthorDetails,
    @SerializedName("content") val content: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("id") val id: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("url") val url: String
)

// TmdbAuthorDetails - Contains additional details about the author of a TMDB review.
data class TmdbAuthorDetails(
    @SerializedName("name") val name: String?,
    @SerializedName("username") val username: String,
    @SerializedName("avatar_path") val avatarPath: String?,
    @SerializedName("rating") val rating: Int?
)