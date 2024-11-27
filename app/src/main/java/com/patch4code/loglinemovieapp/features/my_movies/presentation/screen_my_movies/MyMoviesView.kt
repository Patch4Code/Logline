package com.patch4code.loglinemovieapp.features.my_movies.presentation.screen_my_movies

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.my_movies.domain.model.MyMoviesSortOption
import com.patch4code.loglinemovieapp.features.my_movies.presentation.components.EmptyMyMoviesText
import com.patch4code.loglinemovieapp.features.my_movies.presentation.components.MovieRatedBrowseCard
import com.patch4code.loglinemovieapp.features.my_movies.presentation.components.MyMoviesSortBottomSheet
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.ProvideTopBarBackNavigationIcon
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.ProvideTopBarSortActions
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.ProvideTopBarTitle
import com.patch4code.loglinemovieapp.room_database.LoglineDatabase

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MyMoviesView - Composable function representing the my-movies screen view.
 * Displays the users watched movies.
 *
 * @author Patch4Code
 */
@Composable
fun MyMoviesView(
    navController: NavController,
    db: LoglineDatabase,
    myMoviesViewModel: MyMoviesViewModel = viewModel(
        factory = MyMoviesViewModelFactory(db.movieUserDataDao)
    )
){

    val selectedSortOption = remember { mutableStateOf(MyMoviesSortOption.ByReleaseDateDesc) }
    val showBottomSheet = remember { mutableStateOf(false)  }

    LaunchedEffect(Unit) {
        myMoviesViewModel.getWatchedMovies(selectedSortOption.value)
    }

    // TopBar config
    ProvideTopBarTitle(title = Screen.MyMoviesScreen.title.asString())
    ProvideTopBarBackNavigationIcon(navController)
    ProvideTopBarSortActions(onClickAction = {showBottomSheet.value = true})

    val watchedMoviesItems = myMoviesViewModel.myUserDataList.observeAsState().value

    if(watchedMoviesItems.isNullOrEmpty()){
        EmptyMyMoviesText()
    }else{
        LazyVerticalGrid(
            modifier = Modifier.padding(8.dp),
            columns = GridCells.Fixed(3),
            content = {
                watchedMoviesItems.forEach{
                    item {
                        MovieRatedBrowseCard(navController, it)
                    }
                }
            }
        )
    }
    MyMoviesSortBottomSheet(showBottomSheet, selectedSortOption, myMoviesViewModel)
}