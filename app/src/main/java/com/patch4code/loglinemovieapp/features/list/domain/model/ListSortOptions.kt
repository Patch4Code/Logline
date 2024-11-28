package com.patch4code.loglinemovieapp.features.list.domain.model

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ListElementsSortOptions - Enum representing sorting options for list elements.
 *
 * @author Patch4Code
 */
enum class ListSortOptions(val label: String) {
    ByPositionAsc("List order Asc"),
    ByPositionDesc("List order Desc"),
    ByTitleAsc("Title (A-Z)"),
    ByTitleDesc("Title (Z-A)"),
    ByReleaseDateDesc("Release Date (Newest First)"),
    ByReleaseDateAsc("Release Date (Oldest First)"),
    ByTimeAddedDesc("Time Added (Newest First)"),
    ByTimeAddedAsc("Time Added (Oldest First)"),
}