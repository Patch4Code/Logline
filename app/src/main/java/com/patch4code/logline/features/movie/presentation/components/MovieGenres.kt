package com.patch4code.logline.features.movie.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.patch4code.logline.R
import com.patch4code.logline.features.movie.domain.model.Genre

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MovieGenres - Composable function that displays the genres of the movie.
 *
 * @author Patch4Code
 */
@Composable
fun MovieGenres(genres: List<Genre>?){

    Text(text = stringResource(id = R.string.genres_title), modifier = Modifier.padding(bottom = 4.dp),style = MaterialTheme.typography.titleSmall)
    Row {
        genres?.let {
            Text(text = it.joinToString { genre -> genre.name })
        }
    }
    Spacer(modifier = Modifier.padding(8.dp))
    HorizontalDivider(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))
}