package com.moritz.movieappuitest.userinterface

import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.moritz.movieappuitest.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavController,
    screenTitle: String,
    scrollBehavior: TopAppBarScrollBehavior,
    onDrawerStateChanged: () -> Unit
)
{
    if(screenTitle != "Search"){
        TopAppBar (
            title = {
                Text(text = screenTitle, color = Color.White, fontWeight = FontWeight.Bold)
            },
            navigationIcon = {
                IconButton(onClick = {onDrawerStateChanged()}) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "Open Navigation Drawer")
                }
            },
            actions = {
                IconButton(onClick = { navController.navigate(Screen.SearchScreen.route) }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                }
            },
            scrollBehavior = scrollBehavior
        )
    }
}