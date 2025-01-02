package com.patch4code.loglinemovieapp.features.core.presentation.components.filter_dialog

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.res.stringResource
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieLanguages
import com.patch4code.loglinemovieapp.features.core.presentation.components.base_elements.BaseCountryLanguageSelectionDialog

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * FilterLanguageDialog - Composable function for displaying a dialog to select languages
 * as filter criteria.
 *
 * @author Patch4Code
 */

@Composable
fun FilterLanguageDialog(
    showDialog: MutableState<Boolean>,
    selectedLanguages: SnapshotStateList<String>,
    context: Context,
    languages: Map<String, String> = MovieLanguages.getAllLanguages(context)
) {
    BaseCountryLanguageSelectionDialog(
        showDialog = showDialog,
        items = languages,
        title = stringResource(id = R.string.select_language_label),
        isSelected = {languageKey->
            selectedLanguages.contains(languageKey)
        },
        onItemToggle = { languageKey->
            if (selectedLanguages.contains(languageKey)) {
                selectedLanguages.remove(languageKey)
            } else {
                selectedLanguages.add(languageKey)
            }
        },
        onClose = {
            showDialog.value = false
        }
    )
}