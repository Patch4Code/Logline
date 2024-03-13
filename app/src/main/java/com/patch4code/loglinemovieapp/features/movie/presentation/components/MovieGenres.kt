package com.patch4code.loglinemovieapp.features.movie.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.features.movie.domain.model.Genre

@Composable
fun MovieGenres(genres: List<Genre>?){

    Text(text = "Genre:", color = Color.White, modifier = Modifier.padding(bottom = 4.dp),style = MaterialTheme.typography.titleSmall)
    Row {
        genres?.let {
            Text(text = it.joinToString { genre -> genre.name }, color = Color.White)
        }
    }
    Spacer(modifier = Modifier.padding(8.dp))
    HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))
}