package com.patch4code.logline.features.movie.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.patch4code.logline.R
import com.patch4code.logline.features.movie.domain.model.MovieDetails
import com.patch4code.logline.features.movie.presentation.components.buttons.SmallLinkButton

@Composable
fun MovieOtherSites(movieDetails: MovieDetails?){

    val uriHandler = LocalUriHandler.current

    val movieId = movieDetails?.id
    val imdbId = movieDetails?.imdbId

    val urls = listOf(
        "TMDB" to "$TMDB_BASE_MOVIE_PAGE_URL/$movieId",
        "IMDb" to "https://www.imdb.com/title/$imdbId",
        "Letterboxd" to "https://letterboxd.com/tmdb/$movieId"
    )

    HorizontalDivider(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))
    Column {
        Text(text = stringResource(id = R.string.other_sites_title),
            modifier = Modifier.padding(bottom = 4.dp, top = 4.dp),
            style = MaterialTheme.typography.titleSmall
        )
        Row {
            urls.forEach{ (name, url) ->
                SmallLinkButton(
                    name = name,
                    url = url,
                    enabled = if (name == "IMDb") imdbId != null else movieId != null,
                    uriHandler = uriHandler
                )
            }
        }
    }
}