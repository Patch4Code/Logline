package com.patch4code.logline.features.movie.presentation.components.watch_providers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.patch4code.logline.R
import com.patch4code.logline.features.movie.domain.model.CountryProviders

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MovieWatchProviders - Composable functions that displays the watch providers section for a movie.
 * Shows streaming and rent/buy services and also provides a link to JustWatch for the movie to get
 * more details about the watch providers.
 *
 * @author Patch4Code
 */
@Composable
fun MovieWatchProviders(
    movieProviders: CountryProviders?,
    watchCountry: String?,
    movieId: Int?
){
    if (movieProviders == null) return

    val uriHandler = LocalUriHandler.current

    Row (modifier = Modifier.fillMaxWidth()
        .clickable { uriHandler.openUri("https://www.themoviedb.org/movie/$movieId/watch?locale=$watchCountry") }
    ){
        ProviderSection(movieProviders.flatrate, stringResource(id = R.string.stream_title))
        Spacer(modifier = Modifier.padding(4.dp))
        ProviderSection(movieProviders.rent, stringResource(id = R.string.rent_buy_title))
    }

    // JustWatch reference
    Row(modifier = Modifier.padding(start = 10.dp, top = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = stringResource(id = R.string.source_title))
        Text(text = " JustWatch", fontStyle = FontStyle.Italic)
        Text(text = " ($watchCountry)", fontStyle = FontStyle.Italic, style = MaterialTheme.typography.labelMedium)
    }
    HorizontalDivider(modifier = Modifier.padding(top = 16.dp))
}