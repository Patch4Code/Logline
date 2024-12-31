package com.patch4code.loglinemovieapp.features.search.presentation.components.discover.options

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.patch4code.loglinemovieapp.features.core.presentation.components.base_elements.BaseFilterChipRow
import com.patch4code.loglinemovieapp.features.search.domain.model.DiscoverOptions

@Composable
fun DiscoverLengthSection(discoverOptions: MutableState<DiscoverOptions>) {

    val tmdbShortFilmKeywordId = "263548"

    val itemList = listOf("Feature film", "Short film")

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
        anyChipLabel =  "Any Length",
        onAnyClick = { discoverOptions.value = discoverOptions.value.copy(withKeywords = null, withoutKeywords = null) },
    )
}