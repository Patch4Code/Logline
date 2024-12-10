package com.patch4code.loglinemovieapp.features.list.domain.model

import com.patch4code.loglinemovieapp.features.core.domain.model.SortOption

object ListTableSortOptions {
    val options = listOf(
        SortOption.ByTimeUpdated,
        SortOption.ByListNameAsc,
        SortOption.ByListNameDesc,
        SortOption.ByAddedDesc,
        SortOption.ByAddedAsc
    )
}
