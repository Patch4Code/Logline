package com.patch4code.logline.features.core.presentation.components.base_elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.patch4code.logline.R

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * BaseFilterChipRow - A reusable composable function for displaying a row of filter chips
 * with customizable behavior.
 *
 * This component supports selectable chips for filtering or categorization,
 * with optional "Any" and "Select other" chips.
 *
 * @author Patch4Code
 */

@Composable
fun <T>BaseFilterChipRow(
    items: Iterable<T>,
    labelProvider: (T) -> String,
    isSelected: (T) -> Boolean,
    onItemToggle:(T) -> Unit,
    hasAnyChip: Boolean = false,
    anyChipIsSelected: () -> Boolean = {false},
    anyChipLabel: String = stringResource(id = R.string.any_label),
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
                    label = { Text(stringResource(id = R.string.select_other_label))},
                )
            }
        }
    }
}