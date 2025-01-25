package com.patch4code.logline.features.core.presentation.components.filter_dialog

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.patch4code.logline.R
import com.patch4code.logline.features.core.presentation.components.base_elements.BaseFilterChipRow
import com.patch4code.logline.features.core.presentation.utils.MovieYears

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * FilterDecadeYearSelection - Composable function for selecting decades and years as filter criteria.
 *
 * @author Patch4Code
 */

@Composable
fun FilterDecadeYearSelection(
    selectedDecades: SnapshotStateList<String>,
    selectedYears: SnapshotStateList<String>,
){

    val decades = MovieYears.getDecades()
    val years = MovieYears.getYears()

    val chipWidth = 72.dp

    // any selection
    FilterChip(
        selected = selectedDecades.isEmpty() && selectedYears.isEmpty(),
        onClick = {
            selectedDecades.clear()
            selectedYears.clear()
        },
        label = { Text(stringResource(id = R.string.any_year_label)) }
    )
    
    Spacer(modifier = Modifier.padding(4.dp))
    
    // decade selection
    BaseFilterChipRow(
        items = decades,
        labelProvider = {decade -> decade },
        isSelected = {decade ->
            selectedDecades.contains(decade)
        },
        onItemToggle = {decade ->
            if (selectedDecades.contains(decade)) {
                selectedDecades.remove(decade)
            } else {
                selectedDecades.add(decade)
            }
            selectedYears.clear()
        },
        modifier = Modifier.width(chipWidth)
    )

    Spacer(modifier = Modifier.padding(4.dp))
    
    // year selection
    BaseFilterChipRow(
        items = years,
        labelProvider = {year -> year},
        isSelected = {year->
            selectedYears.contains(year)
        },
        onItemToggle = {year->
            if (selectedYears.contains(year)) {
                selectedYears.remove(year)
            } else {
                selectedYears.add(year)
            }
            selectedDecades.clear()
        },
        modifier = Modifier.width(chipWidth)
    )
}