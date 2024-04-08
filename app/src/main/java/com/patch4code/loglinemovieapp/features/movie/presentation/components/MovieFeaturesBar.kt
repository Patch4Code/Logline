package com.patch4code.loglinemovieapp.features.movie.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.features.movie.domain.model.MovieVideo

const val YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v="

@Composable
fun MovieFeaturesBar(movieVideo: MovieVideo?){

    val uriHandler = LocalUriHandler.current

    val isTrailerButtonEnabled: Boolean = movieVideo != null

    Row {
        FilledTonalButton(
            onClick = { uriHandler.openUri(YOUTUBE_BASE_URL + (movieVideo?.key ?: "")) },
            enabled = isTrailerButtonEnabled
        ) {
            Text(text = "Trailer")
        }
        Spacer(modifier = Modifier.padding(4.dp))
        FilledTonalButton(onClick = { }) {
            Text(text = "Reviews")
        }
        Spacer(modifier = Modifier.padding(4.dp))
        FilledTonalButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.Share, contentDescription = null, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.padding(4.dp))
            Text(text = "Share")
        }
    }
    HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))
}