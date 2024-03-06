package com.moritz.movieappuitest.features.movie.presentation.components.dialogs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.moritz.movieappuitest.features.core.domain.model.Movie
import com.moritz.movieappuitest.features.list.domain.model.MovieList

@Composable
fun AddToListDialogContent(myUserMovieLists: List<MovieList>?, currentMovie: Movie, selectedList: MutableState<MovieList?>){

    LazyColumn(modifier = Modifier.padding(8.dp)) {
        myUserMovieLists?.forEach { list ->
            item {
                val movieAlreadyOnList = list.movies.any { it.id == currentMovie.id }

                TextButton(
                    onClick = { selectedList.value = list },
                    enabled = !movieAlreadyOnList,
                    shape = RectangleShape,
                    //modifier = Modifier.background(color = if(selectedList.value == list) Color.Gray else Color.Unspecified),
                    border = if(selectedList.value == list) BorderStroke(2.dp, Color.Gray ) else null
                ){
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 8.dp)
                    ){

                        Column (modifier = Modifier.weight(1f)
                        ){
                            Text(text = list.name, style = MaterialTheme.typography.titleMedium)
                        }
                        Icon(modifier = Modifier.padding(8.dp),
                            imageVector = if(list.isPublic) Icons.Default.Public else Icons.Default.Lock,
                            contentDescription = if(list.isPublic) "List is public" else "List is private",
                        )
                    }
                }
                HorizontalDivider()
            }
        }
    }
}