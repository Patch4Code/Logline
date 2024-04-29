package com.patch4code.loglinemovieapp.features.movie.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.R

/**
 * APACHE LICENSE, VERSION 2.0 (https://www.apache.org/licenses/LICENSE-2.0)
 *
 * MovieRatings - Composable function that displays the average rating of the movie.
 *
 * @author Patch4Code
 */
@Composable
fun MovieRatings(voteAverage: Double?){

    val voteAverageTmdb: String = String.format("%.1f", voteAverage)

    Column {
        Text(text = stringResource(id = R.string.average_rating_title), modifier = Modifier.padding(bottom = 4.dp),style = MaterialTheme.typography.titleSmall)
        Row {
            Column {
                Text(text = voteAverageTmdb, style = MaterialTheme.typography.titleLarge)
                Text(text = "TMDB")
            }
        }
    }
    HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))
}