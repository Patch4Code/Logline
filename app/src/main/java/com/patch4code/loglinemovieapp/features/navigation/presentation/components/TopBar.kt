package com.patch4code.loglinemovieapp.features.navigation.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavController,
    navViewModel: NavigationViewModel,
    scrollBehavior: TopAppBarScrollBehavior,
    onDrawerStateChanged: () -> Unit
)
{
    val currentScreen by navViewModel.currentScreen.observeAsState(Screen.HomeScreen)

    TopAppBar (
        title = {
            Text(text = currentScreen.title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
        },
        navigationIcon = {
            IconButton(onClick = {onDrawerStateChanged()}) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Open Navigation Drawer")
            }
        },
        scrollBehavior = scrollBehavior
    )
}