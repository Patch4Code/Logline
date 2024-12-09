package com.patch4code.loglinemovieapp.features.my_movies.domain.model

import com.patch4code.loglinemovieapp.features.core.domain.model.SortOption

object MyMoviesSortOptions {
    val options = listOf(
        SortOption.ByReleaseDateDesc,
        SortOption.ByReleaseDateAsc,
        SortOption.ByAddedDesc,
        SortOption.ByAddedAsc,
        SortOption.ByRatingDesc,
        SortOption.ByRatingAsc,
        SortOption.ByTitleAsc,
        SortOption.ByTitleDesc,
        SortOption.ByPopularityDesc,
        SortOption.ByPopularityAsc,
        SortOption.ByVoteAverageDesc,
        SortOption.ByVoteAverageAsc
    )
}