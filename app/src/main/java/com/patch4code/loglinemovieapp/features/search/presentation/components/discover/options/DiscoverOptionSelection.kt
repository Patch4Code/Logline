package com.patch4code.loglinemovieapp.features.search.presentation.components.discover.options

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.search.domain.model.DiscoverOptions
import com.patch4code.loglinemovieapp.features.search.presentation.screen_search.DiscoverViewModel

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * DiscoverOptionSelection - Composable function for
 * managing and displaying discovery filter options.
 *
 * @author Patch4Code
 */
@Composable
fun DiscoverOptionSelection(
    discoverViewModel: DiscoverViewModel,
    discoverOptions: MutableState<DiscoverOptions>,
    listState: LazyListState
){

    Column {
        LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp).weight(1f),
            state = listState
        ) {
            item {
                Text(stringResource(id = R.string.sort_by_text))
                DiscoverSortDropdown(discoverOptions)

                Spacer(modifier = Modifier.padding(8.dp))

                Text(stringResource(id = R.string.genre_text))
                //only Exact genre match
                DiscoverGenreSelection(discoverOptions)

                Spacer(modifier = Modifier.padding(8.dp))

                Text(stringResource(id = R.string.decade_year_text))
                DiscoverDecadeYearSelection(discoverOptions)

                Spacer(modifier = Modifier.padding(8.dp))

                //Origin Country
                Text(stringResource(id = R.string.origin_country_label))
                DiscoverCountrySection(discoverOptions)

                Spacer(modifier = Modifier.padding(8.dp))

                //Original Language
                Text(stringResource(id = R.string.orig_language_text))
                DiscoverLanguageSection(discoverOptions)

                Spacer(modifier = Modifier.padding(8.dp))

                //Service
                Text(stringResource(id = R.string.movie_providers_label))
                DiscoverProviderSection(discoverOptions)

                Spacer(modifier = Modifier.padding(8.dp))

                //Length
                Text(stringResource(id = R.string.length_label))
                DiscoverLengthSection(discoverOptions)

                Spacer(modifier = Modifier.padding(8.dp))

                //TMDB-Rating
                DiscoverRatingSection(discoverOptions)

                Spacer(modifier = Modifier.padding(8.dp))

                //Vote Count
                DiscoverVoteCountSection(discoverOptions)
            }
        }
        Box(modifier = Modifier){
            HorizontalDivider()
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
                horizontalArrangement = Arrangement.Center)  {
                TextButton(onClick = {
                    discoverOptions.value = DiscoverOptions()
                }) {
                    Text(stringResource(id = R.string.reset_capital_label))
                }
                Spacer(modifier = Modifier.padding(start = 32.dp, end = 32.dp))
                Button(onClick = {
                    discoverViewModel.loadDiscoveredMovies(discoverOptions.value) }) {
                    Text(stringResource(id = R.string.discover_movies_label))
                }
            }
        }
    }
}