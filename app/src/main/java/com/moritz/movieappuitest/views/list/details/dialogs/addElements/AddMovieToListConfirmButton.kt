package com.moritz.movieappuitest.views.list.details.dialogs.addElements

import android.widget.Toast
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import com.moritz.movieappuitest.dataclasses.Movie
import com.moritz.movieappuitest.viewmodels.ListViewModel

@Composable
fun AddMovieToListConfirmButton(
    selectedMovie: MutableState<Movie?>,
    listViewModel: ListViewModel,
    onConfirmSuccessful:()->Unit
){

    val context = LocalContext.current

    Button(
        onClick = {
            selectedMovie.value?.let {movie->
                if (listViewModel.isMovieAlreadyOnList(movie)){
                    Toast.makeText(context, "Movie already on the list!", Toast.LENGTH_LONG).show()
                }else{
                    listViewModel.addMovieToList(movie = movie)
                    onConfirmSuccessful()
                }
            }
        },
        enabled = selectedMovie.value != null
    ) {
        Text(text = "Add Movie")
    }
}