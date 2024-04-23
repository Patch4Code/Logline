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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
            contentDescription = "${movieTitle}${stringResource(id = R.string.poster_description_appendage)}",
            error = painterResource(id = R.drawable.movie_poster_placeholder)
        )
        Column (modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)){
            Text(text = movieTitle, style = MaterialTheme.typography.titleLarge)
            Text(text = movieYear, style = MaterialTheme.typography.bodyMedium)
        }
    }
}