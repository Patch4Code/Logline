package com.patch4code.loglinemovieapp.features.search.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchHistoryItem(
    @PrimaryKey val query: String,
    val addTime: Long = System.currentTimeMillis()
)
