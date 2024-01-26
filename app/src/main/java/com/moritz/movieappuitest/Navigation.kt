package com.moritz.movieappuitest

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.moritz.movieappuitest.dataclasses.DrawerNavigationItem
import com.moritz.movieappuitest.userinterface.BottomBar
import com.moritz.movieappuitest.userinterface.MainView
import com.moritz.movieappuitest.userinterface.MovieView
import com.moritz.movieappuitest.userinterface.ProfileView
import com.moritz.movieappuitest.userinterface.SearchView
import com.moritz.movieappuitest.userinterface.SettingsView
import com.moritz.movieappuitest.userinterface.SocialView
import com.moritz.movieappuitest.userinterface.TopBar
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(){

    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    var currentScreenTitle by remember { mutableStateOf("") }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        //Navigation Drawer
        drawerContent = {
            ModalDrawerSheet (
                modifier = Modifier.width(300.dp)
            )
            {
                Spacer(modifier = Modifier.height(16.dp))
                DrawerNavigationItem().getDrawerNavigationItems().forEachIndexed { index, drawerNavigationItem ->
                    NavigationDrawerItem(
                        label = { Text(text = drawerNavigationItem.title) },
                        selected = drawerNavigationItem.title == currentScreenTitle,
                        onClick = {
                            scope.launch{
                                drawerState.close()
                            }
                            navController.navigate(drawerNavigationItem.route)

                        },
                        icon = {
                            Icon(
                                imageVector = if(drawerNavigationItem.title == currentScreenTitle) {
                                    drawerNavigationItem.selectedIcon
                                } else {drawerNavigationItem.unselectedIcon},
                                contentDescription = drawerNavigationItem.title
                            )
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        drawerState = drawerState
    )
    {
        //Screen Content
        Scaffold (
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            bottomBar = {
                BottomBar(navController, currentScreenTitle)
            },
            topBar = {
                    TopBar(navController, currentScreenTitle, scrollBehavior
                    ) {
                        scope.launch{
                            drawerState.open()
                        }
                    }
            }
        )
        {padding ->
            //Navigation handling
            NavHost(
                navController = navController,
                startDestination = Screen.MainScreen.route,
                modifier = Modifier.padding(padding))
            {

                composable(route = Screen.MainScreen.route){
                    MainView(navController = navController)
                    LaunchedEffect(Unit) {
                        currentScreenTitle = Screen.MainScreen.title
                    }
                }

                composable(route = Screen.ProfileScreen.route){
                    ProfileView(navController = navController)
                    LaunchedEffect(Unit) {
                        currentScreenTitle = Screen.ProfileScreen.title
                    }
                }

                composable(route = Screen.SocialScreen.route){
                    SocialView(navController = navController)
                    LaunchedEffect(Unit) {
                        currentScreenTitle = Screen.SocialScreen.title
                    }
                }

                composable(route = Screen.SearchScreen.route){
                    SearchView(navController = navController)
                    LaunchedEffect(Unit) {
                        currentScreenTitle = Screen.SearchScreen.title
                    }
                }
                
                composable(route = Screen.SettingsScreen.route){
                    SettingsView(navController = navController)
                    LaunchedEffect(Unit) {
                        currentScreenTitle = Screen.SettingsScreen.title
                    }
                }

                composable(route = Screen.MovieScreen.route){
                    MovieView(navController = navController)
                    LaunchedEffect(Unit) {
                        currentScreenTitle = Screen.MovieScreen.title
                    }
                }
            }
        }
    }
}