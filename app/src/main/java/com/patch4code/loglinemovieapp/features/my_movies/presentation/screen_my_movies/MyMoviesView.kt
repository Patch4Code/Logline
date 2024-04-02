package com.patch4code.loglinemovieapp.features.my_movies.presentation.screen_my_movies

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.my_movies.presentation.components.MovieRatedBrowseCard
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel
import com.patch4code.loglinemovieapp.room_database.LoglineDatabase

@Composable
fun MyMoviesView(
    navController: NavController,
    navViewModel: NavigationViewModel,
    db: LoglineDatabase,
    myMoviesViewModel: MyMoviesViewModel = viewModel(
        factory = MyMoviesViewModelFactory(db.dao)
    )
){
    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.MyMoviesScreen)
        myMoviesViewModel.setUserdataList()
    }

    val userDataList = myMoviesViewModel.myUserDataList.observeAsState().value

    //Profile Layout
    LazyVerticalGrid(
        modifier = Modifier.padding(8.dp),
        columns = GridCells.Fixed(3),
        content = {
            val ratedItems = userDataList?.filter {it.rating >= 0}
            ratedItems?.forEach{ ratedItem ->
                item {
                    MovieRatedBrowseCard(navController, ratedItem)
                }
            }
        }
    )
}