package com.moritz.movieappuitest

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.moritz.movieappuitest.viewmodels.NavigationViewModel
import com.moritz.movieappuitest.views.DiaryView
import com.moritz.movieappuitest.views.FriendsView
import com.moritz.movieappuitest.views.HomeView
import com.moritz.movieappuitest.views.ListsView
import com.moritz.movieappuitest.views.MovieView
import com.moritz.movieappuitest.views.MyMoviesView
import com.moritz.movieappuitest.views.ProfileEditView
import com.moritz.movieappuitest.views.ProfileView
import com.moritz.movieappuitest.views.ReviewsView
import com.moritz.movieappuitest.views.SearchView
import com.moritz.movieappuitest.views.SettingsView
import com.moritz.movieappuitest.views.WatchlistView
import com.moritz.movieappuitest.views.global.BottomBar
import com.moritz.movieappuitest.views.global.DrawerContent
import com.moritz.movieappuitest.views.global.TopBar
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(){

    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val navigationViewModel: NavigationViewModel = viewModel()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { DrawerContent(navController, navigationViewModel,drawerState, scope, navigationViewModel) },
        content = {
            Scaffold (
                modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
                bottomBar = { BottomBar(navController, navigationViewModel) },
                topBar = {
                    TopBar(navController, navigationViewModel, scrollBehavior) {
                        scope.launch{ drawerState.open() }
                    }
                }
            )
            {padding ->
                //Navigation handling
                NavHost(
                    navController = navController,
                    startDestination = Screen.HomeScreen.route,
                    modifier = Modifier.padding(padding))
                {

                    composable(route = Screen.HomeScreen.route){
                        HomeView(navController = navController, navViewModel = navigationViewModel)
                    }

                    composable(route = Screen.ProfileScreen.route){
                        ProfileView(navController = navController, navViewModel = navigationViewModel)
                    }

                    composable(route = Screen.WatchlistScreen.route){
                        WatchlistView(navController = navController, navViewModel = navigationViewModel)
                    }

                    composable(route = Screen.SearchScreen.route){
                        SearchView(navController = navController, navViewModel = navigationViewModel)
                    }

                    composable(route = Screen.SettingsScreen.route){
                        SettingsView(navController = navController, navViewModel = navigationViewModel)
                    }

                    composable(
                        route = Screen.MovieScreen.route + "/{movie_id}",
                        arguments = listOf(
                            navArgument("movie_id"){
                                type = NavType.StringType
                                defaultValue = "0"
                                nullable = true
                            }
                        )
                    ){parsedId->
                        val movieId = parsedId.arguments?.getString("movie_id")
                        MovieView(
                            navController = navController,
                            navViewModel = navigationViewModel,
                            id = movieId
                        )
                    }

                    composable(route = Screen.MyMoviesScreen.route){
                        MyMoviesView(navController = navController, navViewModel = navigationViewModel)
                    }

                    composable(route = Screen.DiaryScreen.route){
                        DiaryView(navController = navController, navViewModel = navigationViewModel)
                    }

                    composable(route = Screen.ReviewsScreen.route){
                        ReviewsView(navController = navController, navViewModel = navigationViewModel)
                    }

                    composable(route = Screen.ListsScreen.route){
                        ListsView(navController = navController, navViewModel = navigationViewModel)
                    }

                    composable(route = Screen.ProfileEditScreen.route){
                        ProfileEditView(navController = navController, navViewModel = navigationViewModel)
                    }

                    composable(route = Screen.FriendsScreen.route){
                        FriendsView(navController = navController, navViewModel = navigationViewModel)
                    }
                }
            }
        }
    )
}