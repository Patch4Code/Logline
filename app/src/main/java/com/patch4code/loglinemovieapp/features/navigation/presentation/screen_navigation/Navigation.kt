package com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.patch4code.loglinemovieapp.features.about.presentation.screen_settings.AboutView
import com.patch4code.loglinemovieapp.features.diary.presentation.screen_diary.DiaryEditElementView
import com.patch4code.loglinemovieapp.features.diary.presentation.screen_diary.DiaryView
import com.patch4code.loglinemovieapp.features.friends.presentation.screen_friends.FriendsView
import com.patch4code.loglinemovieapp.features.home.presentation.screen_home.HomeView
import com.patch4code.loglinemovieapp.features.list.presentation.screen_list.ListView
import com.patch4code.loglinemovieapp.features.list.presentation.screen_list.ListsTableView
import com.patch4code.loglinemovieapp.features.login.presentation.screen_login.LoginView
import com.patch4code.loglinemovieapp.features.movie.presentation.screen_movie.MovieLogView
import com.patch4code.loglinemovieapp.features.movie.presentation.screen_movie.MovieView
import com.patch4code.loglinemovieapp.features.my_movies.presentation.screen_my_movies.MyMoviesView
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.BottomBar
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.DrawerContent
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.TopBar
import com.patch4code.loglinemovieapp.features.profile.presentation.screen_profile.ProfileEditView
import com.patch4code.loglinemovieapp.features.profile.presentation.screen_profile.ProfileView
import com.patch4code.loglinemovieapp.features.reviews.presentation.screen_reviews.ReviewDetailsView
import com.patch4code.loglinemovieapp.features.reviews.presentation.screen_reviews.ReviewsView
import com.patch4code.loglinemovieapp.features.search.presentation.screen_search.SearchView
import com.patch4code.loglinemovieapp.features.watchlist.presentation.screen_watchlist.WatchlistView
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(){

    val isAuthenticated = remember { mutableStateOf(false)}

    if (!isAuthenticated.value){
        LoginView(onLoginSuccess = {isAuthenticated.value = true})
    }
    else{

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
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection),
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

                        composable(route = Screen.AboutScreen.route){
                            AboutView(navController = navController, navViewModel = navigationViewModel)
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

                        composable(
                            route = Screen.MovieLogScreen.route + "/{movie}",
                            arguments = listOf(
                                navArgument("movie"){
                                    type = NavType.StringType
                                }
                            )
                        ){
                            MovieLogView(
                                navController = navController,
                                navViewModel = navigationViewModel,
                                movieString = it.arguments?.getString("movie"),
                            )
                        }

                        composable(route = Screen.MyMoviesScreen.route){
                            MyMoviesView(navController = navController, navViewModel = navigationViewModel)
                        }

                        composable(route = Screen.DiaryScreen.route){
                            DiaryView(navController = navController, navViewModel = navigationViewModel)
                        }

                        composable(route = Screen.DiaryEditElementScreen.route + "/{loggedElement}/{comingFromDiaryView}",
                            arguments = listOf(
                                navArgument("loggedElement"){
                                    type = NavType.StringType
                                    defaultValue = ""
                                    nullable = true
                                },
                                navArgument("comingFromDiaryView"){ type = NavType.BoolType }
                            )
                        ){parsedLoggedElement->
                            DiaryEditElementView(
                                navController = navController,
                                navViewModel = navigationViewModel,
                                loggedElementId = parsedLoggedElement.arguments?.getString("loggedElement"),
                                comingFromDiaryView = parsedLoggedElement.arguments?.getBoolean("comingFromDiaryView")
                            )
                        }

                        composable(route = Screen.ReviewsScreen.route){
                            ReviewsView(navController = navController, navViewModel = navigationViewModel)
                        }

                        composable(route = Screen.ReviewDetailScreen.route + "/{loggedElement}",
                            arguments = listOf(
                                navArgument("loggedElement"){
                                    type = NavType.StringType
                                    defaultValue = ""
                                    nullable = true
                                }
                            )
                        ){parsedLoggedElement->
                            ReviewDetailsView(navController = navController, navViewModel = navigationViewModel, loggedElementId = parsedLoggedElement.arguments?.getString("loggedElement"))
                        }

                        composable(route = Screen.ListsTableScreen.route){
                            ListsTableView(navController = navController, navViewModel = navigationViewModel)
                        }

                        composable(route = Screen.ListScreen.route + "/{movieList}",
                            arguments = listOf(
                                navArgument("movieList"){
                                    type = NavType.StringType
                                    defaultValue = ""
                                    nullable = true
                                }
                            )
                        ){parsedMovieList->
                            ListView(navController = navController, navViewModel = navigationViewModel, movieListString = parsedMovieList.arguments?.getString("movieList"))
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
}