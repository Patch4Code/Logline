package com.patch4code.loglinemovieapp.features.core.presentation.components.base_elements

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.presentation.components.modifier.customVerticalScrollbar

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * BaseDropdown - Composable function for creating a customizable dropdown menu
 * with a scrollable list of items. Displays a filter chip as the dropdown trigger
 * and allows item selection from a provided list.
 *
 * @author Patch4Code
 */

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
                    Icon(Icons.Default.ArrowDropDown, contentDescription = stringResource(id = R.string.expand_menu_description))
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