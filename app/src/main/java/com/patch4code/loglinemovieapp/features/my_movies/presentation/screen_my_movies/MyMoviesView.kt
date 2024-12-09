package com.patch4code.loglinemovieapp.features.my_movies.presentation.screen_my_movies

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.core.domain.model.FilterOptions
import com.patch4code.loglinemovieapp.features.core.domain.model.SortOption
import com.patch4code.loglinemovieapp.features.core.presentation.components.filter_dialog.SortFilterDialog
import com.patch4code.loglinemovieapp.features.core.presentation.utils.FilterHelper
import com.patch4code.loglinemovieapp.features.core.presentation.utils.sort_filter.FilterOptionsSaver
import com.patch4code.loglinemovieapp.features.core.presentation.utils.sort_filter.SortOptionSaver
import com.patch4code.loglinemovieapp.features.my_movies.domain.model.MyMoviesSortOptions
import com.patch4code.loglinemovieapp.features.my_movies.presentation.components.EmptyMyMoviesText
import com.patch4code.loglinemovieapp.features.my_movies.presentation.components.MovieRatedBrowseCard
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.ProvideTopBarBackNavigationIcon
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.ProvideTopBarSortFilterActions
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

    val selectedSortOption: MutableState<SortOption> =
        rememberSaveable(stateSaver = SortOptionSaver.saver) { mutableStateOf(SortOption.ByReleaseDateDesc) }
    val selectedFilterOptions =
        rememberSaveable(stateSaver = FilterOptionsSaver.saver) { mutableStateOf(FilterOptions()) }

    val showFilterDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        myMoviesViewModel.loadWatchedMovies(selectedSortOption.value, selectedFilterOptions.value)
    }

    // TopBar config
    ProvideTopBarTitle(title = Screen.MyMoviesScreen.title.asString())
    ProvideTopBarBackNavigationIcon(navController)
    ProvideTopBarSortFilterActions(sortFilterOnClickAction = {showFilterDialog.value = true})

    val watchedMoviesItems = myMoviesViewModel.myMoviesItems.observeAsState().value

    if(watchedMoviesItems.isNullOrEmpty()){
        EmptyMyMoviesText(FilterHelper.isAnyFilterApplied(selectedFilterOptions.value))
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
    SortFilterDialog(showFilterDialog, MyMoviesSortOptions.options, selectedSortOption, selectedFilterOptions){
        myMoviesViewModel.loadWatchedMovies(selectedSortOption.value, selectedFilterOptions.value)
    }
}