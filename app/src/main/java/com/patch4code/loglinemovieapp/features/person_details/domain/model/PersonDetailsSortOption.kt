package com.patch4code.loglinemovieapp.features.person_details.domain.model

import com.patch4code.loglinemovieapp.features.core.domain.model.SortOption

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * PersonDetailsSortOptions - Object representing sorting options for person details.
 *
 * @author Patch4Code
 */
object PersonDetailsSortOption{
    val options = listOf(
        SortOption.ByPopularityDesc,
        SortOption.ByPopularityAsc,
        SortOption.ByVoteAverageDesc,
        SortOption.ByVoteAverageAsc,
        SortOption.ByReleaseDateDesc,
        SortOption.ByReleaseDateAsc,
        SortOption.ByTitleAsc,
        SortOption.ByTitleDesc
    )
}