package com.patch4code.loglinemovieapp.features.search.presentation.components.discover

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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.features.core.presentation.components.filter_dialog.customVerticalScrollbar
import com.patch4code.loglinemovieapp.features.search.domain.model.DiscoverOptions
import com.patch4code.loglinemovieapp.features.search.domain.model.DiscoverSortOptions

@Composable
fun DiscoverSortDropdown(
    discoverOptions: MutableState<DiscoverOptions>,
    discoverSortOptions: List<DiscoverSortOptions>
) {
    var expanded by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    val dropdownHeight = 300.dp
    val dropdownItemHeight = 48.dp

    val selectedSortLabel =  DiscoverSortOptions.getLabelFromQuery(discoverOptions.value.sortBy)


    Box {
        FilterChip(
            modifier = Modifier.width(260.dp),
            label = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(selectedSortLabel, modifier = Modifier.weight(1f))
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
                    contentHeight = dropdownItemHeight * discoverSortOptions.size,
                    containerHeight = dropdownHeight,
                    alwaysShowScrollbar = true
                ),
            expanded = expanded,
            onDismissRequest = { expanded = false },
            scrollState = scrollState,
        ) {
            Box {
                Column {
                    discoverSortOptions.forEach { discoverSortOption ->

                        DropdownMenuItem(
                            modifier = Modifier.height(dropdownItemHeight).width(235.dp),
                            onClick = {
                                discoverOptions.value.sortBy = discoverSortOption.queryParam
                                expanded = false
                            },
                            text = { Text(text = discoverSortOption.label) }
                        )
                    }
                }
            }
        }
    }
}