package com.patch4code.loglinemovieapp.features.list.presentation.components.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.presentation.components.swipe.swipeToDeleteContainer
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieList
import com.patch4code.loglinemovieapp.features.list.presentation.components.list.items.ListItem
import com.patch4code.loglinemovieapp.features.list.presentation.screen_list.ListViewModel

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ListContent - Composable function representing the content of a movie list.
 * Includes a header section with list title, makePublic-button and listSettings-button.
 * Below that is a LazyColumn with the list items which provide swipe to delete functionality.
 *
 * @author Patch4Code
 */
@Composable
fun ListContent(
    movieList: MovieList?,
    sortedMovies: List<Movie>,
    openDeleteMovieDialog : MutableState<Boolean>,
    movieToDelete:  MutableState<Movie?>,
    navController: NavController,
    listViewModel: ListViewModel
){

    Column {
        Text(text = movieList?.name ?: "N/A", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleLarge)

        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
        ){
            itemsIndexed(
                items = movieList?.movies ?: emptyList(),
                key = { _, item -> item.hashCode() }
            ) { _, movie ->
                swipeToDeleteContainer(
                    item = movie,
                    onDelete = {
                        movieToDelete.value = movie
                        openDeleteMovieDialog.value = true
                    }
                ) {
                    ListItem(navController, movie)
                }
            }
        }
    }
}