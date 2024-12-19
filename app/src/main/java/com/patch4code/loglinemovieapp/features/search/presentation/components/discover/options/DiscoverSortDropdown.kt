package com.patch4code.loglinemovieapp.features.search.presentation.components.discover.options

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.patch4code.loglinemovieapp.features.core.presentation.components.base_elements.BaseDropdown
import com.patch4code.loglinemovieapp.features.search.domain.model.DiscoverOptions
import com.patch4code.loglinemovieapp.features.search.domain.model.DiscoverSortOptions

@Composable
fun DiscoverSortDropdown(discoverOptions: MutableState<DiscoverOptions>) {

    val discoverSortOptions = DiscoverSortOptions.entries.toList()

    val selectedSortOption = discoverSortOptions.firstOrNull { it.queryParam == discoverOptions.value.sortBy }
        ?: discoverSortOptions.first()

    BaseDropdown(
        selectedItem = selectedSortOption,
        items = discoverSortOptions,
        labelProvider = { it.label },
        onItemSelected = {selectedOption ->
            discoverOptions.value = discoverOptions.value.copy(sortBy = selectedOption.queryParam)
        }
    )
}