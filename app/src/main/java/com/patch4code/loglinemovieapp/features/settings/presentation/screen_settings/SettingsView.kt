package com.patch4code.loglinemovieapp.features.settings.presentation.screen_settings

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.ProvideTopBarBackNavigationIcon
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.ProvideTopBarTitle
import com.patch4code.loglinemovieapp.features.settings.presentation.components.EditProfileButton
import com.patch4code.loglinemovieapp.features.settings.presentation.components.ExportDataSection
import com.patch4code.loglinemovieapp.features.settings.presentation.components.ImportDataSection
import com.patch4code.loglinemovieapp.features.settings.presentation.components.SelectCountrySection

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * SettingsView - Composable function representing the settings screen view.
 * Shows all the available setting-options: button to navigate to the ProfileEditView,
 * a country picker to select the watch providers country, an export option that saves
 * a zip-file with the db-files and an import option to import a previously exported zip-file.
 *
 * @author Patch4Code
 */
@Composable
fun SettingsView(navController: NavController, settingsViewModel: SettingsViewModel = viewModel()
){

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        settingsViewModel.initializeSettingsDataStore(context)
    }

    // TopBar config
    ProvideTopBarTitle(title = Screen.SettingsScreen.title.asString())
    ProvideTopBarBackNavigationIcon(navController)

    LazyColumn{
        item {
            EditProfileButton(navController)
            SelectCountrySection(settingsViewModel)
            ExportDataSection()
            ImportDataSection()
        }
    }
}