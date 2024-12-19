package com.patch4code.loglinemovieapp.features.core.presentation.components.filter_dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieLanguages
import com.patch4code.loglinemovieapp.features.core.presentation.components.BaseFilterChipRow

@Composable
fun FilterLanguageSection(selectedLanguages: SnapshotStateList<String>){

    val showLanguageDialog = remember { mutableStateOf(false) }

    val primaryLanguages = MovieLanguages.getPrimaryLanguages()
    val sortedLanguages = primaryLanguages.entries.sortedBy { it.value }

    BaseFilterChipRow(
        items = sortedLanguages,
        labelProvider = {language-> language.value },
        isSelected = {language->
            selectedLanguages.contains(language.key)
        },
        onItemToggle = {language->
            if (selectedLanguages.contains(language.key)) {
                selectedLanguages.remove(language.key)
            } else {
                selectedLanguages.add(language.key)
            }
        },
        hasAnyChip = true,
        anyChipIsSelected = { selectedLanguages.isEmpty() },
        anyChipLabel = "Any Language",
        onAnyClick = { selectedLanguages.clear() },
        hasSelectOtherButton = true,
        onSelectOtherClick = { showLanguageDialog.value = true },
    )

    FilterLanguageDialog(showLanguageDialog, selectedLanguages)
}