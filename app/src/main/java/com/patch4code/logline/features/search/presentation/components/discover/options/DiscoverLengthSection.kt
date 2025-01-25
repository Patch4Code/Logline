package com.patch4code.logline.features.search.presentation.components.discover.options

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.patch4code.logline.R
import com.patch4code.logline.features.core.presentation.components.base_elements.BaseFilterChipRow
import com.patch4code.logline.features.search.domain.model.DiscoverOptions

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * DiscoverLengthSection - Composable function
 * for managing film length selection in discovery filters.
 *
 * @author Patch4Code
 */
@Composable
fun DiscoverLengthSection(discoverOptions: MutableState<DiscoverOptions>) {

    val tmdbShortFilmKeywordId = "263548"

    val itemList = listOf(stringResource(id = R.string.feature_film_label), stringResource(id = R.string.short_film_label))

    BaseFilterChipRow(
        items = itemList,
        labelProvider = {title->
            title
        },
        isSelected = {title->
            when(title){
                itemList[0] -> !discoverOptions.value.withoutKeywords.isNullOrEmpty()
                itemList[1] -> !discoverOptions.value.withKeywords.isNullOrEmpty()
                else-> false
            }
        },
        onItemToggle = {title->
            when(title){
                itemList[0] -> discoverOptions.value = discoverOptions.value.copy(withKeywords = null, withoutKeywords = tmdbShortFilmKeywordId)
                itemList[1] -> discoverOptions.value = discoverOptions.value.copy(withKeywords = tmdbShortFilmKeywordId, withoutKeywords = null)
            }
        },
        hasAnyChip = true,
        anyChipIsSelected = { discoverOptions.value.withKeywords.isNullOrEmpty() && discoverOptions.value.withoutKeywords.isNullOrEmpty()},
        anyChipLabel =  stringResource(id = R.string.any_length_label),
        onAnyClick = { discoverOptions.value = discoverOptions.value.copy(withKeywords = null, withoutKeywords = null) },
    )
}