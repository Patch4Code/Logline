package com.patch4code.loglinemovieapp.features.list.domain.model

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ListElementsSortOptions - Enum representing sorting options for list elements.
 *
 * @author Patch4Code
 */
enum class ListElementsSortOptions(val label: String) {
    ByOrderDesc("List order"),
    ByTitleAsc("Title (A-Z)"),
    ByTitleDesc("Title (Z-A)"),
    ByReleaseDateDesc("Release Date (Newest First)"),
    ByReleaseDateAsc("Release Date (Oldest First)"),
}