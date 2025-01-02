package com.patch4code.loglinemovieapp.features.my_movies.domain.model

import com.patch4code.loglinemovieapp.features.core.domain.model.SortOption

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ListElementsSortOptions - Object representing sorting options for my movies.
 *
 * @author Patch4Code
 */
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