package com.moritz.movieappuitest.views.movie

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MovieRatings(voteAverage: Double?){

    val voteAverageTmdb: String = String.format("%.1f", voteAverage)

    Column {
        Text(text = "Average Rating:", color = Color.White, modifier = Modifier.padding(bottom = 4.dp),style = MaterialTheme.typography.titleSmall)
        Row {
            Column {
                Text(text = voteAverageTmdb, color = Color.White, style = MaterialTheme.typography.titleLarge)
                Text(text = "TMDB", color = Color.White)
            }
        }
    }
    Divider(color = Color.DarkGray, thickness = 1.dp, modifier = Modifier
        .fillMaxWidth()
        .padding(top = 16.dp, bottom = 16.dp)
    )

}