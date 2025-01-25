package com.patch4code.logline.features.search.presentation.components.discover.options

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.patch4code.logline.R
import com.patch4code.logline.features.core.presentation.components.base_elements.BaseFilterChipRow
import com.patch4code.logline.features.search.domain.model.DiscoverOptions

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * DiscoverProviderSection - Composable function
 * for managing movie provider selections in the discovery filter.
 *
 * @author Patch4Code
 */
@Composable
fun DiscoverProviderSection(discoverOptions: MutableState<DiscoverOptions>) {

    val showProviderDialog = remember { mutableStateOf(false) }

    BaseFilterChipRow(
        items = discoverOptions.value.watchProviders.entries,
        labelProvider = {provider->
            provider.value
        },
        isSelected = { true },
        onItemToggle = {provider->
            val updatedWatchProviders = discoverOptions.value.watchProviders.toMutableMap()
            updatedWatchProviders.remove(provider.key)
            discoverOptions.value = discoverOptions.value.copy(watchProviders = updatedWatchProviders)
        },
        hasAnyChip = true,
        anyChipIsSelected = { discoverOptions.value.watchProviders.isEmpty() },
        anyChipLabel =  stringResource(id = R.string.any_provider_label),
        onAnyClick = { discoverOptions.value = discoverOptions.value.copy(watchProviders= emptyMap()) },
        hasSelectOtherButton = true,
        onSelectOtherClick = { showProviderDialog.value = true },
    )

    DiscoverProviderDialog(showProviderDialog, discoverOptions)
}
