package com.patch4code.loglinemovieapp.features.core.domain.model

sealed class SortOption(val label: String) {
    data object ByAddedDesc : SortOption("Date Added (Newest First)")
    data object ByAddedAsc : SortOption("Date Added (Oldest First)")
    data object ByTitleAsc : SortOption("Title (A-Z)")
    data object ByTitleDesc : SortOption("Title (Z-A)")
    data object ByReleaseDateDesc : SortOption("Release Date (Newest First)")
    data object ByReleaseDateAsc : SortOption("Release Date (Oldest First)")
    data object ByRatingDesc : SortOption("Rating (10-1)")
    data object ByRatingAsc : SortOption("Rating (1-10)")
    data object ByPopularityDesc : SortOption("Popularity (Most Popular first)")
    data object ByPopularityAsc : SortOption("Popularity (Least Popular first)")
    data object ByVoteAverageDesc : SortOption("Average Rating (Highest first)")
    data object ByVoteAverageAsc : SortOption("Average Rating (Lowest first)")
    data object ByPositionAsc : SortOption("List order")
    data object ByTimeUpdated : SortOption("When updated")
    data object ByListNameAsc : SortOption("List name (A-Z)")
    data object ByListNameDesc : SortOption("List name (Z-A)")
}

