package com.patch4code.loglinemovieapp.features.settings.presentation.screen_settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel

@Composable
fun SettingsView(navController: NavController, navViewModel: NavigationViewModel
){

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.SettingsScreen)
    }
    
    Column (modifier = Modifier.padding(8.dp)){

        TextButton(onClick = { navController.navigate(Screen.ProfileEditScreen.route) }
        ) {
            Text(text = "Edit Profile")
            Spacer(modifier = Modifier.padding(4.dp))
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = null)
        }

        //Country selection
        


        //Import Export



    }
    
}