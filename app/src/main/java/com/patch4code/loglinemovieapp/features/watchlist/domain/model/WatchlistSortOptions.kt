package com.patch4code.loglinemovieapp.features.watchlist.domain.model

import com.patch4code.loglinemovieapp.features.core.domain.model.SortOption

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