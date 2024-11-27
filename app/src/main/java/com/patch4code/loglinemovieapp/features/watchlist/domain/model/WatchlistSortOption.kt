package com.patch4code.loglinemovieapp.features.watchlist.domain.model

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * WatchlistSortOption - Enum representing sorting options for the watchlist.
 *
 * @author Patch4Code
 */
enum class WatchlistSortOption(val label: String) {
    ByAddedDesc("Date Added (Newest First)"),
    ByAddedAsc("Date Added (Oldest First)"),
    ByTitleAsc("Title (A-Z)"),
    ByTitleDesc("Title (Z-A)"),
    ByReleaseDateDesc("Release Date (Newest First)"),
    ByReleaseDateAsc("Release Date (Oldest First)"),
}