package com.patch4code.logline.features.settings.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.patch4code.logline.R
import com.patch4code.logline.features.settings.presentation.screen_settings.SettingsViewModel
import com.patch4code.logline.preferences_datastore.StoreSettings

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * SelectCountrySection - Composable function to display the section for selecting a country.
 * On button click the CountryPickerDialog opens.
 *
 * @author Patch4Code
 */
@Composable
fun SelectCountrySection(settingsViewModel: SettingsViewModel){

    val context = LocalContext.current
    val dataSettingsStore = remember { StoreSettings(context) }
    val selectedCountryCode = dataSettingsStore.getWatchProvidersCountry.collectAsState(initial = "").value

    val openCountryPickerDialog = remember { mutableStateOf(false) }

    HorizontalDivider()

    //Country selection
    Column (modifier = Modifier.padding(20.dp)){
        Text(text = stringResource(id = R.string.country_title), style = MaterialTheme.typography.titleMedium)
        Text(text = stringResource(id = R.string.country_text),
            style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.padding(8.dp))
        val selectedCountry = selectedCountryCode?.let { settingsViewModel.getCountryNameByCode(it) }
        Button(onClick = { openCountryPickerDialog.value = true }) {
            Text(text = "$selectedCountry")
        }
    }

    CountryPickerDialog(openCountryPickerDialog, selectedCountryCode, settingsViewModel)
}