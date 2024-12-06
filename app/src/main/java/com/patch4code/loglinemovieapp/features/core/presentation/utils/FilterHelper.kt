package com.patch4code.loglinemovieapp.features.core.presentation.utils

import com.patch4code.loglinemovieapp.features.core.domain.model.FilterOptions

object FilterHelper {
    fun isAnyFilterApplied(filterOptions: FilterOptions): Boolean {
        return filterOptions.selectedGenres.isNotEmpty() ||
                filterOptions.selectedDecades.isNotEmpty() ||
                filterOptions.selectedYears.isNotEmpty() ||
                filterOptions.selectedLanguages.isNotEmpty()
    }

    fun getDecadeFromReleaseDate(releaseDate: String?): String{
        val yearAsStr = MovieHelper.extractYear(releaseDate)
        val yearAsInt = yearAsStr.toIntOrNull()
        if (yearAsInt != null){
            val decade = (yearAsInt/10)*10
            return "${decade}s"
        }else{
            return "N/A"
        }
    }
}