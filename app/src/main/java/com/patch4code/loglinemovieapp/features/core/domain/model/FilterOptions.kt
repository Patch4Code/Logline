package com.patch4code.loglinemovieapp.features.core.domain.model

data class FilterOptions(
    val selectedGenres: List<Int> = emptyList(),
    val selectedDecades: List<String> = emptyList(),
    val selectedYears: List<String> = emptyList(),
    val selectedLanguages: List<String> = emptyList()
)
