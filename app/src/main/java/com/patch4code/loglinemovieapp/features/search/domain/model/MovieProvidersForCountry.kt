package com.patch4code.loglinemovieapp.features.search.domain.model

import com.google.gson.annotations.SerializedName
import com.patch4code.loglinemovieapp.features.movie.domain.model.Provider

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MovieProvidersForCountry - Data class representing the list of movie providers
 * available in a specific country.
 *
 * @author Patch4Code
 */
data class MovieProvidersForCountry(
    @SerializedName("results") val movieProviders: List<Provider>
)
