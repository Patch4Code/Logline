package com.patch4code.loglinemovieapp.features.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun <T>BaseFilterChipRow(
    items: Iterable<T>,
    labelProvider: (T) -> String,
    isSelected: (T) -> Boolean,
    onItemToggle:(T) -> Unit,
    hasAnyChip: Boolean = false,
    anyChipLabel: String = "Any",
    onAnyClick: () -> Unit = {}
){

    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

        if (hasAnyChip){
            item {
                FilterChip(
                    selected = items.none { isSelected(it) },
                    onClick = { onAnyClick() },
                    label = { Text(anyChipLabel) },
                )
            }
        }

        items.forEach{item ->
            item {
                FilterChip(
                    label = { Text(labelProvider(item)) },
                    selected = isSelected(item),
                    onClick = { onItemToggle(item) }
                )
            }
        }
    }
}