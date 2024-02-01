package com.moritz.movieappuitest.views.global

import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.moritz.movieappuitest.viewmodels.NavigationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavController,
    navViewModel: NavigationViewModel,
    scrollBehavior: TopAppBarScrollBehavior,
    onDrawerStateChanged: () -> Unit
)
{
    TopAppBar (
        title = {
            Text(text = navViewModel.currentScreenTitle.value, color = Color.White, fontWeight = FontWeight.Bold)
        },
        navigationIcon = {
            IconButton(onClick = {onDrawerStateChanged()}) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Open Navigation Drawer")
            }
        },
        scrollBehavior = scrollBehavior
    )
}