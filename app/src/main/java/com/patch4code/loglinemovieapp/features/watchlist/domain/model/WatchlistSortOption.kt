package com.patch4code.loglinemovieapp.features.watchlist.domain.model

enum class WatchlistSortOption(val label: String) {
    ByAddedAsc("Date Added (Oldest First)"),
    ByAddedDesc("Date Added (Newest First)"),
    ByTitleAsc("Title (A-Z)"),
    ByTitleDesc("Title (Z-A)"),
    ByReleaseDateAsc("Release Date (Oldest First)"),
    ByReleaseDateDesc("Release Date (Newest First)")
}