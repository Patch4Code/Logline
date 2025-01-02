package com.patch4code.loglinemovieapp.features.search.presentation.components.discover.options

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieGenres
import com.patch4code.loglinemovieapp.features.core.presentation.components.base_elements.BaseFilterChipRow
import com.patch4code.loglinemovieapp.features.search.domain.model.DiscoverOptions

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * DiscoverGenreSelection - A composable function for selecting genres in discovery filters.
 *
 * @author Patch4Code
 */
@Composable
fun DiscoverGenreSelection(discoverOptions: MutableState<DiscoverOptions>){

    val context = LocalContext.current
    val availableGenres:  Map<Int, String> = MovieGenres.getAllGenres(context)

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
        anyChipLabel = stringResource(id = R.string.any_genre_label),
        onAnyClick = {
            discoverOptions.value = discoverOptions.value.copy(genres = emptyList())
        }
    )
}