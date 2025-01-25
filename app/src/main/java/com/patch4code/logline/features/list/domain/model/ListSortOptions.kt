package com.patch4code.logline.features.list.domain.model

import com.patch4code.logline.features.core.domain.model.SortOption

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ListElementsSortOptions - Object representing sorting options for list elements.
 *
 * @author Patch4Code
 */
object ListSortOptions{
    val options = listOf(
        SortOption.ByPositionAsc,
        SortOption.ByTitleAsc,
        SortOption.ByTitleDesc,
        SortOption.ByReleaseDateDesc,
        SortOption.ByReleaseDateAsc,
        SortOption.ByAddedDesc,
        SortOption.ByAddedAsc,
        SortOption.ByPopularityDesc,
        SortOption.ByPopularityAsc,
        SortOption.ByVoteAverageDesc,
        SortOption.ByVoteAverageAsc
    )
}