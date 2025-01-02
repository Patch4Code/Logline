package com.patch4code.loglinemovieapp.features.search.presentation.components.discover.options

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import com.patch4code.loglinemovieapp.features.core.presentation.components.base_elements.BaseDropdown
import com.patch4code.loglinemovieapp.features.search.domain.model.DiscoverOptions
import com.patch4code.loglinemovieapp.features.search.domain.model.DiscoverSortOptions

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * DiscoverSortDropdown - Composable function for selecting sorting options in the discovery filter.
 *
 * @author Patch4Code
 */
@Composable
fun DiscoverSortDropdown(discoverOptions: MutableState<DiscoverOptions>) {

    val context = LocalContext.current

    val discoverSortOptions = DiscoverSortOptions.entries.toList()

    val selectedSortOption = discoverSortOptions.firstOrNull { it.queryParam == discoverOptions.value.sortBy }
        ?: discoverSortOptions.first()

    BaseDropdown(
        selectedItem = selectedSortOption,
        items = discoverSortOptions,
        labelProvider = { it.label.asString(context) },
        onItemSelected = {selectedOption ->
            discoverOptions.value = discoverOptions.value.copy(sortBy = selectedOption.queryParam)
        }
    )
}