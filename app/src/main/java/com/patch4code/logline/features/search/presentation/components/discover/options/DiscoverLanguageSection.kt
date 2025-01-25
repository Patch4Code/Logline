package com.patch4code.logline.features.search.presentation.components.discover.options

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.patch4code.logline.R
import com.patch4code.logline.features.core.domain.model.MovieLanguages
import com.patch4code.logline.features.core.presentation.components.base_elements.BaseFilterChipRow
import com.patch4code.logline.features.search.domain.model.DiscoverOptions

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * DiscoverLanguageSection - Composable function
 * for managing language selection in discovery filters.
 *
 * @author Patch4Code
 */
@Composable
fun DiscoverLanguageSection(discoverOptions: MutableState<DiscoverOptions>){

    val showLanguageDialog = remember { mutableStateOf(false) }

    val context = LocalContext.current
    val primaryLanguages = MovieLanguages.getPrimaryLanguages(context)
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
        anyChipLabel = stringResource(id = R.string.any_Language_label),
        onAnyClick = { discoverOptions.value = discoverOptions.value.copy(originalLanguage = null) },
        hasSelectOtherButton = true,
        onSelectOtherClick = { showLanguageDialog.value = true},
    )

    DiscoverLanguageDialog(showLanguageDialog, discoverOptions, context)
}