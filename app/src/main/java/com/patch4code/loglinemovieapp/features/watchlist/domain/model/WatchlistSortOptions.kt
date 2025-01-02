package com.patch4code.loglinemovieapp.features.watchlist.domain.model

import com.patch4code.loglinemovieapp.features.core.domain.model.SortOption

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * WatchlistSortOptions - Defines sorting options for the watchlist.
 *
 * @author Patch4Code
 */
object WatchlistSortOptions {
    val options = listOf(
        SortOption.ByAddedDesc,
        SortOption.ByAddedAsc,
        SortOption.ByTitleAsc,
        SortOption.ByTitleDesc,
        SortOption.ByReleaseDateDesc,
        SortOption.ByReleaseDateAsc,
        SortOption.ByPopularityDesc,
        SortOption.ByPopularityAsc,
        SortOption.ByVoteAverageDesc,
        SortOption.ByVoteAverageAsc
    )
}