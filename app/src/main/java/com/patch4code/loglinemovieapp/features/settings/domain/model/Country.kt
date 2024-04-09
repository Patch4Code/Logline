package com.patch4code.loglinemovieapp.features.settings.domain.model

data class Country(
    val countryCode: String,
    val countryName: String,
    val flagCode: String = ""
)
