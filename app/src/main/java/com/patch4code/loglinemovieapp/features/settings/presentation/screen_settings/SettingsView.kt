package com.patch4code.loglinemovieapp.features.settings.presentation.screen_settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel
import com.patch4code.loglinemovieapp.features.settings.domain.model.CountryList
import com.patch4code.loglinemovieapp.ui.theme.Beige

@Composable
fun SettingsView(navController: NavController, navViewModel: NavigationViewModel
){

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.SettingsScreen)
    }
    
    Column{

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
            Text(text = "Country for Watch Providers", style = MaterialTheme.typography.titleMedium, color = Beige)
            Text(text = "Select your country to access streaming, rental, and purchase options customized specifically for this location on the movie page.", style = MaterialTheme.typography.bodyMedium,
                color = Beige
            )
            Spacer(modifier = Modifier.padding(8.dp))
            val selected = CountryList.countries.find { it.countryCode == "DE" }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "${selected?.countryName} ${selected?.flagCode}")
            }
        }
        HorizontalDivider()



        //Import Export



    }
    
}