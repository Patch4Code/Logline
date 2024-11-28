package com.patch4code.loglinemovieapp.features.movie.presentation.components.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieInList
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieList
import com.patch4code.loglinemovieapp.features.movie.presentation.screen_movie.AddToListViewModel

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * AddToListDialogContent - Composable function that Displays the content for selecting a list to add a movie to.
 *
 * @author Patch4Code
 */
@Composable
fun AddToListDialogContent(
    addToListViewModel: AddToListViewModel,
    myUserMovieLists: List<MovieList>?,
    currentMovie: Movie,
    moviesInLists: List<MovieInList>?,
    selectedList: MutableState<MovieList?>
){

    LazyColumn(modifier = Modifier.padding(8.dp)) {
        myUserMovieLists?.forEach { list ->
            item {
                // Check if the movie is already on the list
                val movieAlreadyOnList = addToListViewModel.isMovieAlreadyInList(list.id, currentMovie.id)
                val listSize = moviesInLists?.filter { it.movieListId == list.id}?.size ?: 0

                // Display list item as a clickable button
                TextButton(
                    onClick = { selectedList.value = list },
                    enabled = !movieAlreadyOnList,
                    shape = RectangleShape
                ){
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 8.dp)
                    ){

                        // Display list name and movie count of the list
                        Column (modifier = Modifier.weight(1f)
                        ){
                            Row {
                                Text(text = "${list.name} (${listSize})", style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                                if(selectedList.value == list){
                                    Icon(imageVector = Icons.Default.Check, contentDescription = null)
                                }
                            }
                        }
                    }
                }
                HorizontalDivider()
            }
        }
    }
}