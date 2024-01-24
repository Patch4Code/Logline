package com.moritz.movieappuitest.userinterface

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.moritz.movieappuitest.Screen
import com.moritz.movieappuitest.dataclasses.BottomNavigationItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SocialView(navController: NavController){
    val bottomBarItems = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        BottomNavigationItem(
            title = "Profile",
            selectedIcon = Icons.Filled.AccountCircle,
            unselectedIcon = Icons.Outlined.AccountCircle,
        ),
        BottomNavigationItem(
            title = "Social",
            selectedIcon = Icons.Filled.Email,
            unselectedIcon = Icons.Outlined.Email,
        ),
    )
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(2)
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar (
                title = {
                    androidx.compose.material.Text(text = "Home", color = Color.White, fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Open Navigation Drawer")
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            NavigationBar {
                bottomBarItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = {
                            selectedItemIndex = index

                            Log.d("YourTag", selectedItemIndex.toString())
                            //Navigation
                            when (selectedItemIndex){
                                0 -> navController.navigate(Screen.MainScreen.route) //navigate to Home
                                1 -> navController.navigate(Screen.ProfileScreen.route) //navigate to Profile
                                2 -> navController.navigate(Screen.SocialScreen.route) //navigate to Social
                            }
                        },
                        label = {
                            androidx.compose.material.Text(text = item.title, color = Color.White)
                        },
                        alwaysShowLabel = false,
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        }
                    )
                }
            }
        },
        content = { padding ->
            //Profile Layout
            Column(
                modifier = Modifier.padding(padding)
            ){
                Text(text = "Social View")
            }
        }
    )
}



