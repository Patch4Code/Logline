package com.patch4code.logline.features.search.presentation.components.discover.options

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.patch4code.logline.R
import com.patch4code.logline.features.core.domain.model.MovieLanguages
import com.patch4code.logline.features.core.presentation.components.base_elements.BaseCountryLanguageSelectionDialog
import com.patch4code.logline.features.search.domain.model.DiscoverOptions

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * DiscoverLanguageDialog - Composable function for selecting
 * a language in dialog for discovery filters.
 *
 * @author Patch4Code
 */
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
        title = stringResource(id = R.string.select_language_label),
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