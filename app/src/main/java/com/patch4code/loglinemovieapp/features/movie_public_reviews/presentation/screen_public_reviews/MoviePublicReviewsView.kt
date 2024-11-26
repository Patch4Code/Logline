package com.patch4code.loglinemovieapp.features.movie_public_reviews.presentation.screen_public_reviews

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.movie_public_reviews.presentation.components.TmdbMovieReviews
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.ProvideTopBarBackNavigationIcon
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.ProvideTopBarTitle

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MoviePublicReviewsView - Composable function representing the movie-public-reviews screen view.
 * Displays public reviews for a movie.
 *
 * @author Patch4Code
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MoviePublicReviewsView(
    navController: NavController,
    id: String?,
    title: String?,
    moviePublicReviewsViewModel: MoviePublicReviewsViewModel = viewModel()
){
    val movieId = id?.toIntOrNull() ?: -1

    LaunchedEffect(Unit) {
        moviePublicReviewsViewModel.loadTmdbReviews(movieId)
    }

    // TopBar config
    title?.let { ProvideTopBarTitle(title = it) }
    ProvideTopBarBackNavigationIcon(navController)

    // Observe the state of TMDB (and Logline) reviews and loading
    val tmdbMovieReviews = moviePublicReviewsViewModel.tmdbMovieReviews.observeAsState().value
    val tmdbIsLoading = moviePublicReviewsViewModel.tmdbIsLoading.observeAsState().value

    val tabItems = listOf(stringResource(id = R.string.tmdb_reviews_title))
    var selectedTabIndex by remember { mutableStateOf(0) }
    val pagerState = rememberPagerState { tabItems.size }

    //launched on change to selectedTabIndex
    LaunchedEffect(selectedTabIndex){
        pagerState.animateScrollToPage(selectedTabIndex)
    }
    //launched on change to pagerState.currentPage
    LaunchedEffect(pagerState.currentPage){
        selectedTabIndex =pagerState.currentPage
    }

    Column (modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally){
        // TabRow displaying (Logline and) TMDB review tabs
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabItems.forEachIndexed {index, item->
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = { selectedTabIndex = index },
                    text = { Text(text = item) }
                )
            }
        }
        // HorizontalPager displaying Logline and TMDB reviews content and provides swipe-able functionality
        HorizontalPager(state = pagerState, modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
        ) {index->
            Box(modifier = Modifier.fillMaxSize().padding(8.dp)
            ){
                TmdbMovieReviews(tmdbMovieReviews, tmdbIsLoading, moviePublicReviewsViewModel)
            }
        }
    }
}