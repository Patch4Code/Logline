package com.patch4code.logline.features.diary.domain.model

import com.patch4code.logline.features.core.domain.model.SortOption

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * DiaryAndReviewSortOptions - utility object containing
 * predefined sorting options for diary entries and reviews.
 *
 * @author Patch4Code
 */
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