package com.patch4code.loglinemovieapp.features.settings.presentation.screen_settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel
import com.patch4code.loglinemovieapp.features.settings.presentation.components.CountryPickerDialog
import com.patch4code.loglinemovieapp.preferences_datastore.StoreSettings
import com.patch4code.loglinemovieapp.ui.theme.Beige

@Composable
fun SettingsView(navController: NavController, navViewModel: NavigationViewModel, settingsViewModel: SettingsViewModel = viewModel()
){

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.SettingsScreen)
        settingsViewModel.initializeSettingsDataStore(context)
    }

    val dataSettingsStore = remember { StoreSettings(context) }
    val selectedCountryCode = dataSettingsStore.getWatchProvidersCountry.collectAsState(initial = "").value

    val openCountryPickerDialog = remember { mutableStateOf(false) }

    LazyColumn{
        item {
            HorizontalDivider()
            TextButton(onClick = { navController.navigate(Screen.ProfileEditScreen.route) },
                modifier = Modifier.fillMaxWidth(),
                shape = RectangleShape
            ) {
                Column (modifier = Modifier.padding(8.dp)){
                    Row {
                        Text(text = "Edit Profile", style = MaterialTheme.typography.titleMedium)
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = null, modifier = Modifier.size(20.dp))
                    }
                    Text(text = "Customize your local Profile page. Set profile and banner images, update displayed name and bio, and select your top 4 favorite movies.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Beige
                    )
                }
            }
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
            HorizontalDivider()



            //Import Export
            Column (modifier = Modifier.padding(20.dp)){
                Text(text = "Export Data", style = MaterialTheme.typography.titleMedium, color = Beige)
                Text(text = "Export your local Logline App Data as CSV ...", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.padding(8.dp))
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Export Data as CSV")
                }
            }

            HorizontalDivider()

            Column (modifier = Modifier.padding(20.dp)){
                Text(text = "Import CSV-File", style = MaterialTheme.typography.titleMedium, color = Beige)
                Text(text = "Import a CSV file that was exported before", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.padding(8.dp))
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Import CSV")
                }
            }
        }
    }
    CountryPickerDialog(openCountryPickerDialog, selectedCountryCode, settingsViewModel)
}