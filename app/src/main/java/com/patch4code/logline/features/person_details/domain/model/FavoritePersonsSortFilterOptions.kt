package com.patch4code.logline.features.person_details.domain.model

import com.patch4code.logline.features.core.domain.model.SortOption

object FavoritePersonsSortFilterOptions {
    val sortOptions = listOf(
        SortOption.ByNameAsc,
        SortOption.ByNameDesc
    )
}
