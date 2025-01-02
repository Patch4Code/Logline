package com.patch4code.loglinemovieapp.features.core.presentation.components.filter_dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieGenres
import com.patch4code.loglinemovieapp.features.core.presentation.components.base_elements.BaseFilterChipRow

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * FilterGenreSelection - Composable function for selecting movie genres as filter criteria.
 *
 * @author Patch4Code
 */

@Composable
fun FilterGenreSelection(selectedGenres: SnapshotStateList<Int>){

    val context = LocalContext.current
    val availableGenres:  Map<Int, String> = MovieGenres.getAllGenres(context)

    BaseFilterChipRow(
        items = availableGenres.entries,
        labelProvider = { it.value },
        isSelected = {selectedGenres.contains(it.key)},
        onItemToggle = {
            if (selectedGenres.contains(it.key)) {
                selectedGenres.remove(it.key)
            } else {
                selectedGenres.add(it.key)
            }
        },
        hasAnyChip = true,
        anyChipIsSelected = { selectedGenres.isEmpty() },
        anyChipLabel = stringResource(id = R.string.any_genre_label),
        onAnyClick = { selectedGenres.clear() }
    )
}