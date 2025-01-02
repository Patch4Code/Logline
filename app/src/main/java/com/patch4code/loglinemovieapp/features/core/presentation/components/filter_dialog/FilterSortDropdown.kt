package com.patch4code.loglinemovieapp.features.core.presentation.components.filter_dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import com.patch4code.loglinemovieapp.features.core.domain.model.SortOption
import com.patch4code.loglinemovieapp.features.core.presentation.components.base_elements.BaseDropdown

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * FilterSortDropdown - Composable function for displaying
 * a dropdown menu to select a sorting option.
 *
 * @author Patch4Code
 */

@Composable
fun FilterSortDropdown(
    tempSelectedSortOption: MutableState<SortOption>,
    sortOptions: List<SortOption>
) {

    val context = LocalContext.current
    BaseDropdown(
        selectedItem = tempSelectedSortOption.value,
        items = sortOptions,
        labelProvider = { it.label.asString(context) },
        onItemSelected = {selectedOption ->
            tempSelectedSortOption.value = selectedOption }
    )
}