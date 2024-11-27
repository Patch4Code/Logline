package com.patch4code.loglinemovieapp.features.diary.domain.model

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * DiarySortOptions - Enum representing sorting options for the diary.
 *
 * @author Patch4Code
 */
enum class DiaryAndReviewSortOptions(val label: String) {
    ByAddedDesc("Log Date (Newest First)"),
    ByAddedAsc("Log Date (Oldest First)"),
    ByTitleAsc("Title (A-Z)"),
    ByTitleDesc("Title (Z-A)"),
    ByReleaseDateDesc("Release Date (Newest First)"),
    ByReleaseDateAsc("Release Date (Oldest First)"),
    ByRatingDesc("Rating (10-1)"),
    ByRatingAsc("Rating (1-10)")
}