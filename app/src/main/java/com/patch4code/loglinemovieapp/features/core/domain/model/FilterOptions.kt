package com.patch4code.loglinemovieapp.features.core.domain.model

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * FilterOptions - Data class representing filtering options for movies.
 *
 * @author Patch4Code
 */

data class FilterOptions(
    val selectedGenres: List<Int> = emptyList(),
    val selectedDecades: List<String> = emptyList(),
    val selectedYears: List<String> = emptyList(),
    val selectedLanguages: List<String> = emptyList()
)
