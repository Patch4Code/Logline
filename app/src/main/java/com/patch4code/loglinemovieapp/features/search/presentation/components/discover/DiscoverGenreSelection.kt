package com.patch4code.loglinemovieapp.features.search.presentation.components.discover

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.patch4code.loglinemovieapp.features.core.presentation.components.BaseFilterChipRow
import com.patch4code.loglinemovieapp.features.search.domain.model.DiscoverOptions

@Composable
fun DiscoverGenreSelection(
    availableGenres: Map<Int, String>,
    discoverOptions: MutableState<DiscoverOptions>
){
    BaseFilterChipRow(
        items = availableGenres.entries,
        labelProvider = { it.value },
        isSelected = { discoverOptions.value.genres.contains(it.key) },
        onItemToggle = {
            val updatedGenres = discoverOptions.value.genres.toMutableList()
            if (discoverOptions.value.genres.contains(it.key)) {
                updatedGenres.remove(it.key)
            } else {
                updatedGenres.add(it.key)
            }
            discoverOptions.value = discoverOptions.value.copy(genres = updatedGenres)
        },
        hasAnyChip = true,
        anyChipLabel = "Any Genre",
        onAnyClick = {
            discoverOptions.value = discoverOptions.value.copy(genres = emptyList())
        }
    )
}