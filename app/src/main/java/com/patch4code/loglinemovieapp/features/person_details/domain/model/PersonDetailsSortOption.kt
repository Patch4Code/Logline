package com.patch4code.loglinemovieapp.features.person_details.domain.model

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * PersonDetailsSortOptions - Enum representing sorting options for person details.
 *
 * @author Patch4Code
 */
enum class PersonDetailsSortOption(val label: String) {
    ByPopularityDesc("Popularity (Most Popular first)"),
    ByPopularityAsc("Popularity (Least Popular first)"),
    ByReleaseDateDesc("Release Date (Newest First)"),
    ByReleaseDateAsc("Release Date (Oldest First)"),
    ByTitleAsc("Title (A-Z)"),
    ByTitleDesc("Title (Z-A)"),
}