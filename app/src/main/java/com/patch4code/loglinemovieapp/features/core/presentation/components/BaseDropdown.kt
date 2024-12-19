package com.patch4code.loglinemovieapp.features.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> BaseDropdown(
    selectedItem: T,
    items: List<T>,
    labelProvider: (T) -> String,
    onItemSelected: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    val dropdownHeight = 300.dp
    val dropdownItemHeight = 48.dp

    Box {
        FilterChip(
            modifier = Modifier.width(260.dp),
            label = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(labelProvider(selectedItem), modifier = Modifier.weight(1f))
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Expand Menu")
                }
            },
            selected = true,
            onClick = { expanded = !expanded }
        )
        DropdownMenu(
            modifier = Modifier
                .heightIn(max = dropdownHeight)
                .customVerticalScrollbar(
                    state = scrollState,
                    contentHeight = dropdownItemHeight * items.size,
                    containerHeight = dropdownHeight,
                    alwaysShowScrollbar = true
                ),
            expanded = expanded,
            onDismissRequest = { expanded = false },
            scrollState = scrollState,
        ) {
            Box {
                Column {
                    items.forEach { item ->
                        DropdownMenuItem(
                            modifier = Modifier.height(dropdownItemHeight).width(235.dp),
                            onClick = {
                                onItemSelected(item)
                                expanded = false
                            },
                            text = { Text(text = labelProvider(item)) }
                        )
                    }
                }
            }
        }
    }
}
