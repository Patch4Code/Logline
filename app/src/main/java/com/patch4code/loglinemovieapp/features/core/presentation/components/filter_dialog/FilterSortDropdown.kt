package com.patch4code.loglinemovieapp.features.core.presentation.components.filter_dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.patch4code.loglinemovieapp.features.core.domain.model.SortOption
import com.patch4code.loglinemovieapp.features.core.presentation.components.base_elements.BaseDropdown

@Composable
fun FilterSortDropdown(
    tempSelectedSortOption: MutableState<SortOption>,
    sortOptions: List<SortOption>
) {

    BaseDropdown(
        selectedItem = tempSelectedSortOption.value,
        items = sortOptions,
        labelProvider = { it.label },
        onItemSelected = {selectedOption ->
            tempSelectedSortOption.value = selectedOption }
    )
}