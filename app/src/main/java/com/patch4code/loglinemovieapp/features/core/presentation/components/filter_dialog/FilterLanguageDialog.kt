package com.patch4code.loglinemovieapp.features.core.presentation.components.filter_dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieLanguages
import com.patch4code.loglinemovieapp.features.core.presentation.components.base_elements.BaseLanguageSelectionDialog

@Composable
fun FilterLanguageDialog(
    showDialog: MutableState<Boolean>,
    selectedLanguages: SnapshotStateList<String>,
    languages: Map<String, String> = MovieLanguages.getAllLanguages()
) {
    BaseLanguageSelectionDialog(
        showDialog = showDialog,
        items = languages,
        title = "Select a Language",
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