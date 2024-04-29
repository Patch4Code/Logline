package com.patch4code.loglinemovieapp.features.movie.domain.model

import com.google.gson.annotations.SerializedName

/**
 * APACHE LICENSE, VERSION 2.0 (https://www.apache.org/licenses/LICENSE-2.0)
 *
 * WatchProvides - Represents the availability of a movie on different streaming platforms from TMDB in cooperation with JustWatch.
 *
 * @author Patch4Code
 */
data class WatchProvides(
    @SerializedName("id") val id: Int = -1,
    @SerializedName("results") val availableServicesMap: Map<String, CountryProviders>
)

// CountryProviders - Represents the streaming providers available in a specific country.
data class CountryProviders(
    @SerializedName("link") val link: String = "",
    @SerializedName("flatrate") val flatrate: List<Provider>? = null,
    @SerializedName("buy") val buy: List<Provider>? = null,
    @SerializedName("rent") val rent: List<Provider>? = null,
    @SerializedName("ads") val ads: List<Provider>? = null
)

// Provider - Represents a streaming provider.
data class Provider(
    @SerializedName("logo_path") val logoPath: String,
    @SerializedName("provider_id") val providerId: Int,
    @SerializedName("provider_name") val providerName: String,
    @SerializedName("display_priority") val displayPriority: Int
)