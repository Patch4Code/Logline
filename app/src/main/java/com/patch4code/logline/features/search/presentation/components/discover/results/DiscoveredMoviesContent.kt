package com.patch4code.logline.features.search.presentation.components.discover.results

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.patch4code.logline.R
import com.patch4code.logline.features.core.domain.model.Movie
import com.patch4code.logline.features.core.presentation.components.cards.MovieGridBrowseCard
import com.patch4code.logline.features.core.presentation.components.load.LoadErrorDisplay
import com.patch4code.logline.features.core.presentation.components.load.LoadingIndicator
import com.patch4code.logline.features.search.domain.model.DiscoverOptions
import com.patch4code.logline.features.search.presentation.screen_search.DiscoverViewModel
import kotlinx.coroutines.launch

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * DiscoveredMoviesContent - Displays discovered movies in a grid layout
 * with support for loading, error handling, and navigation.
 *
 * @author Patch4Code
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoveredMoviesContent(
    discoveredMovies: List<Movie>?,
    isLoading: Boolean?,
    hasError: Boolean?,
    discoverOptions: DiscoverOptions,
    discoverViewModel: DiscoverViewModel,
    navController: NavController,
    gridState: LazyGridState
){

    val scope = rememberCoroutineScope()

    BackHandler {
        discoverViewModel.clearDiscoveredMovies()
        scope.launch {
            gridState.scrollToItem(0)
        }
    }


    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()


    Column(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.discovered_movies_title),
                    style = MaterialTheme.typography.titleSmall
                )
            },
            navigationIcon = {
                IconButton(onClick = { discoverViewModel.clearDiscoveredMovies() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_icon_description)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface,
                scrolledContainerColor = MaterialTheme.colorScheme.surface
            ),
            expandedHeight = 36.dp,
            scrollBehavior = scrollBehavior

        )

        when {
            isLoading == true -> LoadingIndicator()
            hasError == true -> LoadErrorDisplay(onReload = {
                discoverViewModel.loadDiscoveredMovies(discoverOptions)
            })
            discoveredMovies.isNullOrEmpty() -> EmptyDiscoverText()
            else ->
                LazyVerticalGrid(
                    modifier = Modifier.padding(8.dp),
                    columns = GridCells.Fixed(3),
                    state = gridState,
                    content = {
                        discoveredMovies.forEachIndexed {index, movie ->
                            item {
                                MovieGridBrowseCard(navController, movie)
                                if(index == discoveredMovies.lastIndex - 2){
                                    discoverViewModel.loadMoreDiscoveredMovies(discoverOptions)
                                }
                            }
                        }
                    }
                )
        }
    }
}