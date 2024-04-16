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
import com.patch4code.loglinemovieapp.features.social.presentation.screen_public_profiles.PublicProfilesView
import com.patch4code.loglinemovieapp.features.social.presentation.screen_public_reviews.PublicReviewDetailsView
import com.patch4code.loglinemovieapp.features.social.presentation.screen_public_reviews.PublicReviewsView
import com.patch4code.loglinemovieapp.features.social.presentation.screen_social.SocialView
import com.patch4code.loglinemovieapp.features.watchlist.presentation.screen_watchlist.WatchlistView
import com.patch4code.loglinemovieapp.room_database.LoglineDatabase
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(db: LoglineDatabase){

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
                        ProfileView(navController = navController, navViewModel = navigationViewModel, db = db)
                    }

                    composable(route = Screen.WatchlistScreen.route){
                        WatchlistView(navController = navController, navViewModel = navigationViewModel, db = db)
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
                            navViewModel = navigationViewModel,
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
                            navViewModel = navigationViewModel,
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
                            navViewModel = navigationViewModel,
                            id = movieId,
                            title = movieTitle
                        )
                    }

                    composable(route = Screen.MyMoviesScreen.route){
                        MyMoviesView(navController = navController, navViewModel = navigationViewModel, db = db)
                    }

                    composable(route = Screen.DiaryScreen.route){
                        DiaryView(navController = navController, navViewModel = navigationViewModel, db = db)
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
                            db = db,
                            loggedElementId = parsedLoggedElement.arguments?.getString("loggedElement"),
                            comingFromDiaryView = parsedLoggedElement.arguments?.getBoolean("comingFromDiaryView")
                        )
                    }

                    composable(route = Screen.ReviewsScreen.route){
                        ReviewsView(navController = navController, navViewModel = navigationViewModel, db = db)
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
                            navViewModel = navigationViewModel,
                            db = db,
                            loggedElementId = parsedLoggedElement.arguments?.getString("loggedElement")
                        )
                    }

                    composable(route = Screen.ListsTableScreen.route){
                        ListsTableView(navController = navController, navViewModel = navigationViewModel, db = db)
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
                        ListView(
                            navController = navController,
                            navViewModel = navigationViewModel,
                            db = db,
                            movieListString = parsedMovieList.arguments?.getString("movieList")
                        )
                    }

                    composable(route = Screen.ProfileEditScreen.route){
                        ProfileEditView(navController = navController, navViewModel = navigationViewModel, db = db)
                    }

                    composable(route = Screen.SocialScreen.route){
                        SocialView(navController = navController, navViewModel = navigationViewModel, db = db)
                    }

                    composable(route = Screen.PublicProfilesScreen.route){
                        PublicProfilesView(navController = navController, navViewModel = navigationViewModel)
                    }

                    composable(route = Screen.PublicReviewsScreen.route){
                        PublicReviewsView(navController = navController, navViewModel = navigationViewModel)
                    }

                    composable(
                        route = Screen.PublicReviewDetailsScreen.route + "/{loglineReview}",
                        arguments = listOf(
                            navArgument("loglineReview"){
                                type = NavType.StringType
                            }
                        )
                        ){
                        PublicReviewDetailsView(
                            navController = navController,
                            navViewModel = navigationViewModel,
                            loglineReviewJson = it.arguments?.getString("loglineReview")
                        )
                    }

                    composable(route = Screen.SettingsScreen.route){
                        SettingsView(navController = navController, navViewModel = navigationViewModel)
                    }
                }
            }
        }
    )
}