package com.patch4code.loglinemovieapp.features.diary.domain.model

import com.patch4code.loglinemovieapp.features.core.domain.model.SortOption

object DiaryAndReviewSortOptions {
    val options = listOf(
        SortOption.ByAddedDesc,
        SortOption.ByAddedAsc,
        SortOption.ByReleaseDateDesc,
        SortOption.ByReleaseDateAsc,
        SortOption.ByRatingDesc,
        SortOption.ByRatingAsc,
        SortOption.ByTitleAsc,
        SortOption.ByTitleDesc,
        SortOption.ByPopularityDesc,
        SortOption.ByPopularityAsc,
        SortOption.ByVoteAverageDesc,
        SortOption.ByVoteAverageAsc,
    )
}