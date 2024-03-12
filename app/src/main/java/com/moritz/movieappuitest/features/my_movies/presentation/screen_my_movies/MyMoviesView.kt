package com.moritz.movieappuitest.features.my_movies.presentation.screen_my_movies

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.moritz.movieappuitest.features.core.domain.model.userDataList
import com.moritz.movieappuitest.features.my_movies.presentation.components.MovieRatedBrowseCard
import com.moritz.movieappuitest.features.navigation.domain.model.Screen
import com.moritz.movieappuitest.features.navigation.presentation.screen_navigation.NavigationViewModel

@Composable
fun MyMoviesView(navController: NavController, navViewModel: NavigationViewModel){

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.MyMoviesScreen)
    }
    //Profile Layout
    LazyVerticalGrid(
        modifier = Modifier.padding(8.dp),
        columns = GridCells.Fixed(3),
        content = {
            val ratedItems = userDataList.filter {it.rating >= 0}
            ratedItems.forEach{ ratedItem ->
                item {
                    MovieRatedBrowseCard(navController, ratedItem)
                }
            }
        }
    )
}