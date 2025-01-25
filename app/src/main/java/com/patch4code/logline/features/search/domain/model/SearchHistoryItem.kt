package com.patch4code.logline.features.search.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * SearchHistoryItem - Data class representing a search history entry.
 *
 * @author Patch4Code
 */
@Entity
data class SearchHistoryItem(
    @PrimaryKey val query: String,
    val addTime: Long = System.currentTimeMillis()
)
