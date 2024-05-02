package com.patch4code.loglinemovieapp.features.settings.domain.model

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * Country - Representing information about a country used for selection of watch provider country.
 *
 * @author Patch4Code
 */
data class Country(
    val countryCode: String,
    val countryName: String,
    val flagCode: String = ""
)
