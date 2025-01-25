package com.patch4code.logline.features.search.presentation.components.search.results

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.patch4code.logline.features.core.domain.model.Movie

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * SearchResultsList - Composable for displaying a list of search results.
 *
 * @author Patch4Code
 */
@Composable
fun SearchResultsList(
    searchResult: List<Movie>,
    navController: NavController,
    listState: LazyListState,
    onEndReached: () -> Unit
){
    LazyColumn(state = listState) {
        searchResult.forEachIndexed { index, movie->
            item{
                MovieSearchCard(navController, movie)
                if(index == searchResult.lastIndex){
                    onEndReached()
                }
            }
        }
    }
}