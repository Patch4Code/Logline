@file:Suppress("UNCHECKED_CAST")

package com.patch4code.logline.features.core.presentation.utils.sort_filter

import androidx.compose.runtime.saveable.Saver
import com.patch4code.logline.features.core.domain.model.FilterOptions


object FilterOptionsSaver {
    val saver = Saver<FilterOptions, Map<String, Any>>(
        save = { filterOptions ->
            mapOf(
                "selectedGenres" to filterOptions.selectedGenres,
                "selectedDecades" to filterOptions.selectedDecades,
                "selectedYears" to filterOptions.selectedYears,
                "selectedLanguages" to filterOptions.selectedLanguages
            )
        },
        restore = { savedMap ->
            FilterOptions(
                selectedGenres = savedMap["selectedGenres"] as List<Int>,
                selectedDecades = savedMap["selectedDecades"] as List<String>,
                selectedYears = savedMap["selectedYears"] as List<String>,
                selectedLanguages = savedMap["selectedLanguages"] as List<String>
            )
        }
    )
}