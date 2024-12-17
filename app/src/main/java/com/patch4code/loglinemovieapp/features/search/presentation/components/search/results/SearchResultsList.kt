package com.patch4code.loglinemovieapp.features.search.presentation.components.search.results

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie

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