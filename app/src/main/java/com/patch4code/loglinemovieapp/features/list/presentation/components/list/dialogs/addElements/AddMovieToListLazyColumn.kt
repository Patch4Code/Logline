package com.patch4code.loglinemovieapp.features.list.presentation.components.list.dialogs.addElements

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.list.presentation.components.list.items.MovieListAddMovieCard

@Composable
fun AddMovieToListLazyColumn(searchResult: List<Movie>?, selectedMovie: MutableState<Movie?>){

    LazyColumn (modifier = Modifier.padding(top = 8.dp)) {
        searchResult?.forEach{ movie->
            item{
                Card(colors = CardDefaults.cardColors(containerColor = if(selectedMovie.value == movie) Color.Gray else Color.Transparent)) {
                    MovieListAddMovieCard(
                        movie = movie,
                        selectMovie = {clickedMovie->
                            selectedMovie.value = clickedMovie
                        }
                    )
                }
            }
        }
    }
}