package com.patch4code.loglinemovieapp.features.search.presentation.components.discover.options

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieLanguages
import com.patch4code.loglinemovieapp.features.core.presentation.components.base_elements.BaseCountryLanguageSelectionDialog
import com.patch4code.loglinemovieapp.features.search.domain.model.DiscoverOptions

@Composable
fun DiscoverLanguageDialog(
    showDialog: MutableState<Boolean>,
    discoverOptions: MutableState<DiscoverOptions>,
    context: Context,
    languages: Map<String, String> = MovieLanguages.getAllLanguages(context)
){
    BaseCountryLanguageSelectionDialog(
        showDialog = showDialog,
        items = languages,
        title = "Select a Language",
        isSelected = { languageKey->
            discoverOptions.value.originalLanguage == languageKey
        },
        onItemToggle = { languageKey->
            if (discoverOptions.value.originalLanguage == languageKey) {
                discoverOptions.value = discoverOptions.value.copy(originalLanguage = null)
            } else {
                discoverOptions.value = discoverOptions.value.copy(originalLanguage = languageKey)
            }
        },
        onClose = { showDialog.value = false }
    )
}