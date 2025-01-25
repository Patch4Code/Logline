package com.patch4code.logline.features.search.presentation.components.discover.options

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.patch4code.logline.R
import com.patch4code.logline.features.core.domain.model.MovieCountries
import com.patch4code.logline.features.core.presentation.components.base_elements.BaseCountryLanguageSelectionDialog
import com.patch4code.logline.features.core.presentation.components.base_elements.BaseFilterChipRow
import com.patch4code.logline.features.search.domain.model.DiscoverOptions

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * DiscoverCountrySection - composable function to
 * manage and display country selection for discovery filters.
 *
 * @author Patch4Code
 */
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
        anyChipLabel = stringResource(id = R.string.any_country_label),
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
        title = stringResource(id = R.string.select_country_label),
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