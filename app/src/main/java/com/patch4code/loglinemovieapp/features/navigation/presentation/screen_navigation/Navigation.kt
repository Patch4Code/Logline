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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.patch4code.loglinemovieapp.features.about.presentation.screen_settings.AboutView
import com.patch4code.loglinemovieapp.features.diary.presentation.screen_diary.DiaryEditElementView
import com.patch4code.loglinemovieapp.features.diary.presentation.screen_diary.DiaryView
import com.patch4code.loglinemovieapp.features.home.presentation.screen_home.HomeView
import com.patch4code.loglinemovieapp.features.list.presentation.screen_list.ListView
import com.patch4code.loglinemovieapp.features.list.presentation.screen_list.ListsTableView
import com.patch4code.loglinemovieapp.features.movie.presentation.screen_movie.MovieLogView
import com.patch4code.loglinemovieapp.features.movie.presentation.screen_movie.MovieView
import com.patch4code.loglinemovieapp.features.movie_public_reviews.presentation.screen_public_reviews.MoviePublicReviewsView
import com.patch4code.loglinemovieapp.features.my_movies.presentation.screen_my_movies.MyMoviesView
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.BottomBar
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.DrawerContent
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.TopBar
import com.patch4code.loglinemovieapp.features.person_details.presentation.screen_person.PersonDetailsView
import com.patch4code.loglinemovieapp.features.profile.presentation.screen_profile.ProfileEditView
import com.patch4code.loglinemovieapp.features.profile.presentation.screen_profile.ProfileView
import com.patch4code.loglinemovieapp.features.reviews.presentation.screen_reviews.ReviewDetailsView
import com.patch4code.loglinemovieapp.features.reviews.presentation.screen_reviews.ReviewsView
import com.patch4code.loglinemovieapp.features.search.presentation.screen_search.SearchView
import com.patch4code.loglinemovieapp.features.settings.presentation.screen_settings.SettingsView
import com.patch4code.loglinemovieapp.features.watchlist.presentation.screen_watchlist.WatchlistView
import com.patch4code.loglinemovieapp.room_database.LoglineDatabase
import kotlinx.coroutines.launch

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * Navigation - Handles the navigation within the app using Jetpack Compose navigation components.
 * This function sets up the navigation graph and defines all available destinations.
 * Also provides superordinate ui of top-bar, bottom-bar and navigationDrawer.
 *
 * @author Patch4Code
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(db: LoglineDatabase){

    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val searchFocusRequest = remember { mutableStateOf(false) }
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val isSearchView = currentRoute == Screen.SearchScreen.route //used to fix SearchView TabRow

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { DrawerContent(navController,drawerState, scope) },
        content = {
            Scaffold (
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                bottomBar = { BottomBar(navController, searchFocusRequest) },
                topBar = {
                    TopBar(navController, if(!isSearchView) scrollBehavior else null) {
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
                        HomeView(navController = navController)
                    }

                    composable(route = Screen.ProfileScreen.route){
                        ProfileView(navController = navController, db = db)
                    }

                    composable(route = Screen.WatchlistScreen.route){
                        WatchlistView(navController = navController, db = db)
                    }

                    composable(route = Screen.SearchScreen.route){
                        SearchView(
                            navController = navController,
                            searchFocusRequest = searchFocusRequest
                        )
                    }

                    composable(route = Screen.AboutScreen.route){
                        AboutView(navController)
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
                            db = db,
                            id = movieId
                        )
                    }

                    composable(
                        route = Screen.PersonDetailsScreen.route + "/{person_id}",
                        arguments = listOf(
                            navArgument("person_id"){
                                type = NavType.IntType
                            }
                        )
                    ){
                        PersonDetailsView(
                            navController = navController,
                            id = it.arguments?.getInt("person_id")
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
                            db = db,
                            movieString = it.arguments?.getString("movie"),
                        )
                    }

                    composable(
                        route = Screen.MoviePublicReviewsScreen.route + "/{movie_id}/{movie_title}",
                        arguments = listOf(navArgument("movie_id"){ type = NavType.StringType })
                    ){parsedId->
                        val movieId = parsedId.arguments?.getString("movie_id")
                        val movieTitle = parsedId.arguments?.getString("movie_title")
                        MoviePublicReviewsView(
                            navController = navController,
                            id = movieId,
                            title = movieTitle
                        )
                    }

                    composable(route = Screen.MyMoviesScreen.route){
                        MyMoviesView(navController = navController, db = db)
                    }

                    composable(route = Screen.DiaryScreen.route){
                        DiaryView(navController = navController, db = db)
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
                            db = db,
                            loggedElementId = parsedLoggedElement.arguments?.getString("loggedElement"),
                            comingFromDiaryView = parsedLoggedElement.arguments?.getBoolean("comingFromDiaryView")
                        )
                    }

                    composable(route = Screen.ReviewsScreen.route){
                        ReviewsView(navController = navController, db = db)
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
                        ReviewDetailsView(
                            navController = navController,
                            db = db,
                            loggedElementId = parsedLoggedElement.arguments?.getString("loggedElement")
                        )
                    }

                    composable(route = Screen.ListsTableScreen.route){
                        ListsTableView(navController = navController, db = db)
                    }

                    composable(route = Screen.ListScreen.route + "/{listId}",
                        arguments = listOf(
                            navArgument("listId"){
                                type = NavType.StringType
                                defaultValue = ""
                                nullable = true
                            }
                        )
                    ){parsedMovieList->
                        ListView(
                            navController = navController,
                            db = db,
                            listId = parsedMovieList.arguments?.getString("listId")
                        )
                    }

                    composable(route = Screen.ProfileEditScreen.route){
                        ProfileEditView(navController = navController, db = db)
                    }

                    composable(route = Screen.SettingsScreen.route){
                        SettingsView(navController = navController)
                    }
                }
            }
        }
    )
}