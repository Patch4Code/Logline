package com.patch4code.loglinemovieapp.features.movie.domain.model

import com.google.gson.annotations.SerializedName

data class WatchProvides(
    @SerializedName("id") val id: Int = -1,
    @SerializedName("results") val availableServicesMap: Map<String, CountryProviders>
)

data class CountryProviders(
    @SerializedName("link") val link: String = "",
    @SerializedName("flatrate") val flatrate: List<Provider>? = null,
    @SerializedName("buy") val buy: List<Provider>? = null,
    @SerializedName("rent") val rent: List<Provider>? = null,
    @SerializedName("ads") val ads: List<Provider>? = null
)

data class Provider(
    @SerializedName("logo_path") val logoPath: String,
    @SerializedName("provider_id") val providerId: Int,
    @SerializedName("provider_name") val providerName: String,
    @SerializedName("display_priority") val displayPriority: Int
)