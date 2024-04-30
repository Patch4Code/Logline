package com.patch4code.loglinemovieapp.features.core.domain.model

import com.google.gson.annotations.SerializedName

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MoviePage - Represents a page of movie data retrieved from TMDB
 *
 * @author Patch4Code
 */

data class MoviePage(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<Movie>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)
