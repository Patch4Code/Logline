package com.patch4code.loglinemovieapp.features.search.presentation.components.discover.options

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieGenres
import com.patch4code.loglinemovieapp.features.core.presentation.components.base_elements.BaseFilterChipRow
import com.patch4code.loglinemovieapp.features.search.domain.model.DiscoverOptions

@Composable
fun DiscoverGenreSelection(discoverOptions: MutableState<DiscoverOptions>){

    val availableGenres:  Map<Int, String> = MovieGenres.getAllGenres()

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
        anyChipIsSelected = { discoverOptions.value.genres.isEmpty() },
        anyChipLabel = "Any Genre",
        onAnyClick = {
            discoverOptions.value = discoverOptions.value.copy(genres = emptyList())
        }
    )
}