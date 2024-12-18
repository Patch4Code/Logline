package com.patch4code.loglinemovieapp.features.core.presentation.components.base_elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun <T>BaseFilterChipRow(
    items: Iterable<T>,
    labelProvider: (T) -> String,
    isSelected: (T) -> Boolean,
    onItemToggle:(T) -> Unit,
    hasAnyChip: Boolean = false,
    anyChipIsSelected: () -> Boolean = {false},
    anyChipLabel: String = "Any",
    onAnyClick: () -> Unit = {},
    hasSelectOtherButton: Boolean = false,
    onSelectOtherClick:() -> Unit = {},
    modifier: Modifier = Modifier
){

    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

        if (hasAnyChip){
            item {
                FilterChip(
                    modifier = modifier,
                    selected =  anyChipIsSelected(),
                    onClick = { onAnyClick() },
                    label = { Text(text = anyChipLabel, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) }
                )
            }
        }

        items.forEach{item ->
            item {
                FilterChip(
                    modifier = modifier,
                    selected = isSelected(item),
                    onClick = { onItemToggle(item) },
                    label = { Text(text= labelProvider(item), textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) }
                )
            }
        }

        if(hasSelectOtherButton){
            item {
                FilterChip(
                    selected = false,
                    onClick = { onSelectOtherClick() },
                    label = { Text("Select other")},
                )
            }
        }
    }
}