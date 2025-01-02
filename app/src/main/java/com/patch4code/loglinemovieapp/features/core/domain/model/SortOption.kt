package com.patch4code.loglinemovieapp.features.core.domain.model

import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.presentation.utils.UiText

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * SortOption - Sealed class representing various sorting options for movies and lists.
 *
 * @author Patch4Code
 */

sealed class SortOption(val label: UiText) {
    data object ByAddedDesc : SortOption(UiText.StringResource(R.string.sort_by_added_desc))
    data object ByAddedAsc : SortOption(UiText.StringResource(R.string.sort_by_added_asc))
    data object ByTitleAsc : SortOption(UiText.StringResource(R.string.sort_by_title_asc))
    data object ByTitleDesc : SortOption(UiText.StringResource(R.string.sort_by_title_desc))
    data object ByReleaseDateDesc : SortOption(UiText.StringResource(R.string.sort_by_release_date_desc))
    data object ByReleaseDateAsc : SortOption(UiText.StringResource(R.string.sort_by_release_date_asc))
    data object ByRatingDesc : SortOption(UiText.StringResource(R.string.sort_by_rating_desc))
    data object ByRatingAsc : SortOption(UiText.StringResource(R.string.sort_by_rating_asc))
    data object ByPopularityDesc : SortOption(UiText.StringResource(R.string.sort_by_popularity_desc))
    data object ByPopularityAsc : SortOption(UiText.StringResource(R.string.sort_by_popularity_asc))
    data object ByVoteAverageDesc : SortOption(UiText.StringResource(R.string.sort_by_vote_average_desc))
    data object ByVoteAverageAsc : SortOption(UiText.StringResource(R.string.sort_by_vote_average_asc))
    data object ByPositionAsc : SortOption(UiText.StringResource(R.string.sort_by_position_asc))
    data object ByTimeUpdated : SortOption(UiText.StringResource(R.string.sort_by_time_updated))
    data object ByListNameAsc : SortOption(UiText.StringResource(R.string.sort_by_list_name_asc))
    data object ByListNameDesc : SortOption(UiText.StringResource(R.string.sort_by_list_name_desc))
}