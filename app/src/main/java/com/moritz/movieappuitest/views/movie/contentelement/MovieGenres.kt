package com.moritz.movieappuitest.views.movie.contentelement

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.moritz.movieappuitest.dataclasses.Genre

@Composable
fun MovieGenres(genres: List<Genre>?){

    Text(text = "Genre:", color = Color.White, modifier = Modifier.padding(bottom = 4.dp),style = MaterialTheme.typography.titleSmall)
    Row {
        genres?.let {
            Text(text = it.joinToString { genre -> genre.name }, color = Color.White)
        }
    }
    Spacer(modifier = Modifier.padding(8.dp))
    Divider(color = Color.DarkGray, thickness = 1.dp, modifier = Modifier
        .fillMaxWidth()
        .padding(top = 16.dp, bottom = 16.dp)
    )
}