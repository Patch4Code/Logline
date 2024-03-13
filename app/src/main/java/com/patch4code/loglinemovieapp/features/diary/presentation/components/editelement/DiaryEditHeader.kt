package com.patch4code.loglinemovieapp.features.diary.presentation.components.editelement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.patch4code.loglinemovieapp.R

@Composable
fun DiaryEditHeader(movieTitle: String, moviePosterUrl: String, movieYear: String){
    Row (modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)){
        AsyncImage(
            model = moviePosterUrl,
            contentDescription = "${movieTitle}-Poster",
            error = painterResource(id = R.drawable.movie_poster_placeholder)
        )
        Column (modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)){
            Text(text = movieTitle, color = Color.White, style = MaterialTheme.typography.titleLarge)
            Text(text = movieYear, color = Color.White, style = MaterialTheme.typography.bodyMedium)
        }
    }
}