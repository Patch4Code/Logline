package com.patch4code.loglinemovieapp.features.core.presentation.components.filter_dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieLanguages
import com.patch4code.loglinemovieapp.features.core.presentation.components.base_elements.BaseFilterChipRow

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * FilterLanguageSection - Composable function for displaying a language selection section
 * with primary languages and an option to select more.
 *
 * @author Patch4Code
 */

@Composable
fun FilterLanguageSection(selectedLanguages: SnapshotStateList<String>){

    val showLanguageDialog = remember { mutableStateOf(false) }

    val context = LocalContext.current
    val primaryLanguages = MovieLanguages.getPrimaryLanguages(context)
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
        anyChipLabel = stringResource(id = R.string.any_Language_label),
        onAnyClick = { selectedLanguages.clear() },
        hasSelectOtherButton = true,
        onSelectOtherClick = { showLanguageDialog.value = true },
    )

    FilterLanguageDialog(showLanguageDialog, selectedLanguages, context)
}