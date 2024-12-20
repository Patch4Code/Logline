package com.patch4code.loglinemovieapp.features.search.presentation.components.discover.options

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.patch4code.loglinemovieapp.features.core.presentation.components.base_elements.BaseFilterChipRow
import com.patch4code.loglinemovieapp.features.search.domain.model.DiscoverOptions


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
        anyChipLabel =  "Any Provider",
        onAnyClick = { discoverOptions.value = discoverOptions.value.copy(watchProviders= emptyMap()) },
        hasSelectOtherButton = true,
        onSelectOtherClick = { showProviderDialog.value = true },
    )

    DiscoverProviderDialog(showProviderDialog, discoverOptions)
}
