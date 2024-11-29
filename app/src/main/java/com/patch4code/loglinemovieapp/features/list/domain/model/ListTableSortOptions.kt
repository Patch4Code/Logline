package com.patch4code.loglinemovieapp.features.list.domain.model

enum class ListTableSortOptions(val label: String) {
    ByTimeUpdated("When updated"),
    ByNameAsc("List name (A-Z)"),
    ByNameDesc("List name (Z-A)"),
    ByTimeCreatedDesc("Time Added (Newest First)"),
    ByTimeCreatedAsc("Time Added (Oldest First)"),
}