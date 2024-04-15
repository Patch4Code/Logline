package com.patch4code.loglinemovieapp.features.movie_public_reviews.domain.model

import com.google.gson.annotations.SerializedName

data class TmdbReviewResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<TmdbReview>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

data class TmdbReview(
    @SerializedName("author") val author: String,
    @SerializedName("author_details") val tmdbAuthorDetails: TmdbAuthorDetails,
    @SerializedName("content") val content: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("id") val id: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("url") val url: String
)

data class TmdbAuthorDetails(
    @SerializedName("name") val name: String?,
    @SerializedName("username") val username: String,
    @SerializedName("avatar_path") val avatarPath: String?,
    @SerializedName("rating") val rating: Int?
)