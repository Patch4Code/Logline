package com.patch4code.loglinemovieapp.features.movie.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MovieRatings(voteAverage: Double?){

    val voteAverageTmdb: String = String.format("%.1f", voteAverage)

    Column {
        Text(text = "Average Rating:", modifier = Modifier.padding(bottom = 4.dp),style = MaterialTheme.typography.titleSmall)
        Row {
            Column {
                Text(text = voteAverageTmdb, style = MaterialTheme.typography.titleLarge)
                Text(text = "TMDB")
            }
        }
    }
    HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))
}