package com.patch4code.loglinemovieapp.features.search.presentation.components.discover.options

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieCountries
import com.patch4code.loglinemovieapp.features.core.presentation.components.base_elements.BaseCountryLanguageSelectionDialog
import com.patch4code.loglinemovieapp.features.core.presentation.components.base_elements.BaseFilterChipRow
import com.patch4code.loglinemovieapp.features.search.domain.model.DiscoverOptions

@Composable
fun DiscoverCountrySection(discoverOptions: MutableState<DiscoverOptions>) {

    val showCountryDialog = remember { mutableStateOf(false) }

    val context = LocalContext.current
    val primaryCountries = MovieCountries.getPrimaryCountries(context)
    val sortedPrimaryCountries = primaryCountries.entries.sortedBy { it.value }

    BaseFilterChipRow(
        items = sortedPrimaryCountries,
        labelProvider = {country-> country.value },
        isSelected = {country->
            discoverOptions.value.originCountry == country.key
        },
        onItemToggle = {country->
            if(discoverOptions.value.originCountry == country.key){
                discoverOptions.value = discoverOptions.value.copy(originCountry = null)

            }else{
                discoverOptions.value = discoverOptions.value.copy(originCountry = country.key)
            }
        },
        hasAnyChip = true,
        anyChipIsSelected = { discoverOptions.value.originCountry == null },
        anyChipLabel = "Any Country",
        onAnyClick = { discoverOptions.value = discoverOptions.value.copy(originCountry = null) },
        hasSelectOtherButton = true,
        onSelectOtherClick = { showCountryDialog.value = true },
    )

    DiscoverCountryDialog(showCountryDialog, discoverOptions, context)
}

@Composable
fun DiscoverCountryDialog(
    showDialog: MutableState<Boolean>,
    discoverOptions: MutableState<DiscoverOptions>,
    context: Context,
    countries: Map<String, String> = MovieCountries.geAllCountries(context)
) {
    BaseCountryLanguageSelectionDialog(
        showDialog = showDialog,
        items = countries,
        title = "Select a Country",
        isSelected = { countryKey->
            discoverOptions.value.originCountry == countryKey
        },
        onItemToggle = { countryKey->
            if (discoverOptions.value.originCountry == countryKey) {
                discoverOptions.value = discoverOptions.value.copy(originCountry = null)
            } else {
                discoverOptions.value = discoverOptions.value.copy(originCountry = countryKey)
            }
        },
        onClose = { showDialog.value = false }
    )
}