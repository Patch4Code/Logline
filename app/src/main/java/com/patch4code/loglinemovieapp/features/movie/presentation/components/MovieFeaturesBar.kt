package com.patch4code.loglinemovieapp.features.movie.presentation.components

import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.movie.domain.model.MovieDetails
import com.patch4code.loglinemovieapp.features.movie.domain.model.MovieVideo
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen

const val YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v="
const val TMDB_BASE_MOVIE_PAGE_URL = "https://www.themoviedb.org/movie"

@Composable
fun MovieFeaturesBar(movieVideo: MovieVideo?, movieDetails: MovieDetails?, navController: NavController){

    val context = LocalContext.current

    val uriHandler = LocalUriHandler.current
    val isTrailerButtonEnabled: Boolean = movieVideo != null

    val movieId = movieDetails?.id ?: -1
    val title = movieDetails?.title

    val movieUrl = "$TMDB_BASE_MOVIE_PAGE_URL/$movieId"
    val message = "$title\n$movieUrl"
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, message)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)

    Row {
        FilledTonalButton(
            onClick = { uriHandler.openUri(YOUTUBE_BASE_URL + (movieVideo?.key ?: "")) },
            enabled = isTrailerButtonEnabled
        ) {
            Text(text = "Trailer")
        }
        Spacer(modifier = Modifier.padding(4.dp))

        FilledTonalButton(onClick = { navController.navigate(Screen.MoviePublicReviewsScreen.withArgs("$movieId/$title")) }) {
            Text(text = "Reviews")
        }
        Spacer(modifier = Modifier.padding(4.dp))

        FilledTonalButton(onClick = {  startActivity(context, shareIntent, null) }) {
            Icon(imageVector = Icons.Default.Share, contentDescription = null, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.padding(4.dp))
            Text(text = "Share")
        }
    }
    HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))
}