package com.patch4code.loglinemovieapp.features.search.presentation.components.discover.options

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieLanguages
import com.patch4code.loglinemovieapp.features.core.presentation.components.base_elements.BaseFilterChipRow
import com.patch4code.loglinemovieapp.features.search.domain.model.DiscoverOptions

@Composable
fun DiscoverLanguageSection(discoverOptions: MutableState<DiscoverOptions>){

    val showLanguageDialog = remember { mutableStateOf(false) }

    val primaryLanguages = MovieLanguages.getPrimaryLanguages()
    val sortedLanguages = primaryLanguages.entries.sortedBy { it.value }

    BaseFilterChipRow(
        items = sortedLanguages,
        labelProvider = {language-> language.value},
        isSelected = {language->
            discoverOptions.value.originalLanguage == language.key
        },
        onItemToggle = {language->
            if (discoverOptions.value.originalLanguage == language.key) {
                discoverOptions.value = discoverOptions.value.copy(originalLanguage = null)
            } else {
                discoverOptions.value = discoverOptions.value.copy(originalLanguage = language.key)
            }
        },
        hasAnyChip = true,
        anyChipIsSelected = { discoverOptions.value.originalLanguage == null },
        anyChipLabel = "Any Language",
        onAnyClick = { discoverOptions.value = discoverOptions.value.copy(originalLanguage = null) },
        hasSelectOtherButton = true,
        onSelectOtherClick = { showLanguageDialog.value = true},
    )

    DiscoverLanguageDialog(showLanguageDialog, discoverOptions)
}