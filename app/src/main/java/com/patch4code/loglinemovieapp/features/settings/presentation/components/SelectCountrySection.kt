package com.patch4code.loglinemovieapp.features.settings.presentation.components

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
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.features.settings.presentation.screen_settings.SettingsViewModel
import com.patch4code.loglinemovieapp.preferences_datastore.StoreSettings

@Composable
fun SelectCountrySection(settingsViewModel: SettingsViewModel){

    val context = LocalContext.current
    val dataSettingsStore = remember { StoreSettings(context) }
    val selectedCountryCode = dataSettingsStore.getWatchProvidersCountry.collectAsState(initial = "").value

    val openCountryPickerDialog = remember { mutableStateOf(false) }

    HorizontalDivider()

    //Country selection
    Column (modifier = Modifier.padding(20.dp)){
        Text(text = "Country for Watch Providers", style = MaterialTheme.typography.titleMedium)
        Text(text = "Select your country to access streaming, rental, and purchase options customized specifically for this location on the movie page.",
            style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.padding(8.dp))
        val selectedCountry = selectedCountryCode?.let { settingsViewModel.getCountryNameByCode(it) }
        Button(onClick = { openCountryPickerDialog.value = true }) {
            Text(text = "$selectedCountry")
        }
    }

    CountryPickerDialog(openCountryPickerDialog, selectedCountryCode, settingsViewModel)
}