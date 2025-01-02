package com.patch4code.loglinemovieapp.features.list.domain.model

import com.patch4code.loglinemovieapp.features.core.domain.model.SortOption

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ListElementsSortOptions - Object representing sorting options for the list-table.
 *
 * @author Patch4Code
 */
object ListTableSortOptions {
    val options = listOf(
        SortOption.ByTimeUpdated,
        SortOption.ByListNameAsc,
        SortOption.ByListNameDesc,
        SortOption.ByAddedDesc,
        SortOption.ByAddedAsc
    )
}
