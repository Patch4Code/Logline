package com.patch4code.loglinemovieapp.features.search.domain.model

import com.google.gson.annotations.SerializedName
import com.patch4code.loglinemovieapp.features.movie.domain.model.Provider

data class MovieProvidersForCountry(
    @SerializedName("results") val movieProviders: List<Provider>
)
