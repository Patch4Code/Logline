package com.patch4code.loglinemovieapp.features.search.domain.model

import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.presentation.utils.UiText

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * DiscoverSortOptions - Enum class defining available sorting options for movie discovery.
 *
 * @author Patch4Code
 */
enum class DiscoverSortOptions(val queryParam: String, val label: UiText){
    POPULARITY_DESC("popularity.desc", UiText.StringResource(R.string.sort_by_popularity_desc)),
    POPULARITY_ASC("popularity.asc", UiText.StringResource(R.string.sort_by_popularity_asc)),
    VOTE_AVERAGE_DESC("vote_average.desc", UiText.StringResource(R.string.sort_by_vote_average_desc)),
    VOTE_AVERAGE_ASC("vote_average.asc", UiText.StringResource(R.string.sort_by_vote_average_asc)),
    RELEASE_DATE_DESC("primary_release_date.desc", UiText.StringResource(R.string.sort_by_release_date_desc)),
    RELEASE_DATE_ASC("primary_release_date.asc", UiText.StringResource(R.string.sort_by_release_date_asc)),
    REVENUE_DESC("revenue.desc", UiText.StringResource(R.string.sort_by_revenue_desc));
}
