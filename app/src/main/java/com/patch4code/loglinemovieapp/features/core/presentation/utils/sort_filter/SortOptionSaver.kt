package com.patch4code.loglinemovieapp.features.core.presentation.utils.sort_filter

import androidx.compose.runtime.saveable.Saver
import com.patch4code.loglinemovieapp.features.core.domain.model.SortOption

object SortOptionSaver {
    val saver = Saver<SortOption, String>(
        save = { it::class.simpleName!! },
        restore = { className ->
            when (className) {
                "ByAddedDesc" -> SortOption.ByAddedDesc
                "ByAddedAsc" -> SortOption.ByAddedAsc
                "ByTitleAsc" -> SortOption.ByTitleAsc
                "ByTitleDesc" -> SortOption.ByTitleDesc
                "ByReleaseDateDesc" -> SortOption.ByReleaseDateDesc
                "ByReleaseDateAsc" -> SortOption.ByReleaseDateAsc
                "ByRatingDesc" -> SortOption.ByRatingDesc
                "ByRatingAsc" -> SortOption.ByRatingAsc
                "ByPopularityDesc" -> SortOption.ByPopularityDesc
                "ByPopularityAsc" -> SortOption.ByPopularityAsc
                "ByVoteAverageDesc" -> SortOption.ByVoteAverageDesc
                "ByVoteAverageAsc" -> SortOption.ByVoteAverageAsc
                "ByPositionAsc" -> SortOption.ByPositionAsc
                "ByTimeUpdated" -> SortOption.ByTimeUpdated
                "ByListNameAsc" -> SortOption.ByListNameAsc
                "ByListNameDesc" -> SortOption.ByListNameDesc
                else -> throw IllegalArgumentException("Unknown SortOption: $className")
            }
        }
    )
}