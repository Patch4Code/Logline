package com.patch4code.loglinemovieapp.features.settings.presentation.screen_settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel
import com.patch4code.loglinemovieapp.features.settings.presentation.components.EditProfileButton
import com.patch4code.loglinemovieapp.features.settings.presentation.components.ExportDataSection
import com.patch4code.loglinemovieapp.features.settings.presentation.components.SelectCountrySection
import com.patch4code.loglinemovieapp.ui.theme.Beige

@Composable
fun SettingsView(navController: NavController, navViewModel: NavigationViewModel, settingsViewModel: SettingsViewModel = viewModel()
){

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.SettingsScreen)
        settingsViewModel.initializeSettingsDataStore(context)
    }

    LazyColumn{
        item {
            EditProfileButton(navController)
            HorizontalDivider()
            SelectCountrySection(settingsViewModel)

            //Import Export
            ExportDataSection()

            HorizontalDivider()

            Column (modifier = Modifier.padding(20.dp)){
                Text(text = "Import Database", style = MaterialTheme.typography.titleMedium, color = Beige)
                Text(text = "Import file that was exported before", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.padding(8.dp))
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Import")
                }
            }
        }
    }

}