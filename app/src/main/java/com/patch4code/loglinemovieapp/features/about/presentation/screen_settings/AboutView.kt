package com.patch4code.loglinemovieapp.features.about.presentation.screen_settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel

@Composable
fun AboutView(navViewModel: NavigationViewModel){

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.AboutScreen)
    }

    val uriHandler = LocalUriHandler.current

    Column(modifier = Modifier.padding(16.dp))
    {
        Column (modifier = Modifier
            .fillMaxWidth()
            .size(150.dp)
            .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Icon(painter = painterResource(id = R.drawable.loglinelogo_v5), contentDescription = null)
        }

        Text(text = stringResource(id = R.string.extended_app_name), style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.padding(4.dp))
        Text(text = stringResource(id = R.string.logline_info), style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.padding(8.dp))

        ElevatedButton(onClick = { uriHandler.openUri("https://github.com/Patch4Code/Logline") }) {
           Text(text = stringResource(id = R.string.logline_github_button_text))
        }

        Spacer(modifier = Modifier.padding(16.dp))

        Image(
            painter = painterResource(id = R.drawable.tmdb_logo),
            contentDescription = null,
            modifier = Modifier.width(240.dp)
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text(text = stringResource(id = R.string.tmdb_credits_text))
        Spacer(modifier = Modifier.padding(8.dp))
        ElevatedButton(onClick = { uriHandler.openUri("https://www.themoviedb.org/terms-of-use") }) {
            Text(text = stringResource(id = R.string.tmdb_terms_of_use_button_text))
        }
        ElevatedButton(onClick = { uriHandler.openUri("https://www.themoviedb.org/api-terms-of-use") }) {
            Text(text = stringResource(id = R.string.tmdb_api_terms_of_use_button_text))
        }
    }
}