package com.patch4code.loglinemovieapp.features.core.presentation.components.filter_dialog

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieYears
import com.patch4code.loglinemovieapp.features.core.presentation.components.base_elements.BaseFilterChipRow

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
        label = { Text("Any Year") }
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