package com.patch4code.loglinemovieapp.features.search.domain.model

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * DiscoverDecade - Data class representing a decade for discovery filtering.
 *
 * @author Patch4Code
 */
data class DiscoverDecade(
    val decade: String,
    val decadeStartDate: String,
    val decadeEndDate: String
)
