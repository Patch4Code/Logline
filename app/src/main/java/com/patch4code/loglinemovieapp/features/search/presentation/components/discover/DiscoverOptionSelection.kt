package com.patch4code.loglinemovieapp.features.search.presentation.components.discover

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieGenres
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieLanguages
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieYears
import com.patch4code.loglinemovieapp.features.search.domain.model.DiscoverOptions
import com.patch4code.loglinemovieapp.features.search.domain.model.DiscoverSortOptions
import com.patch4code.loglinemovieapp.features.search.presentation.screen_search.DiscoverViewModel

@Composable
fun DiscoverOptionSelection(
    discoverViewModel: DiscoverViewModel,
    discoverOptions: MutableState<DiscoverOptions>
){


    val discoverSortOptions = DiscoverSortOptions.entries.toList()

    val availableGenres:  Map<Int, String> = MovieGenres.getAllGenres()


    val discoverDecades = MovieYears.getDiscoverDecades()
    val years = MovieYears.getYears()

    val originCountries = ""
    val primaryLanguages = MovieLanguages.getPrimaryLanguages()
    val showLanguageDialog = remember { mutableStateOf(false) }

    //Services (load from API)

    //length

    //tmdb rating

    //vote count

    //peron and company

    Column {

        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .weight(1f)) {
            item {
                Text("Sort by")
                DiscoverSortDropdown(discoverOptions, discoverSortOptions)

                Spacer(modifier = Modifier.padding(8.dp))

                Text("Genre")
                //only Exact genre match
                DiscoverGenreSelection(availableGenres, discoverOptions)

                Spacer(modifier = Modifier.padding(8.dp))

                Text("Decade/Year")
                DiscoverDecadeYearSelection(discoverOptions, discoverDecades, years)

                //Origin Country

                //Original Language
                Spacer(modifier = Modifier.padding(8.dp))
                Text("Original Language")

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        FilterChip(
                            selected = discoverOptions.value.originalLanguage == null,
                            onClick = { discoverOptions.value = discoverOptions.value.copy(originalLanguage = null) },
                            label = { Text("Any Language")},
                        )
                    }
                    val sortedLanguages = primaryLanguages.entries.sortedBy { it.value }
                    sortedLanguages.forEach{language ->
                        item {
                            FilterChip(
                                label = {Text(language.value)},
                                selected = discoverOptions.value.originalLanguage == language.key,
                                onClick = {
                                    if (discoverOptions.value.originalLanguage == language.key) {
                                        discoverOptions.value = discoverOptions.value.copy(originalLanguage = null)
                                    } else {
                                        discoverOptions.value = discoverOptions.value.copy(originalLanguage = language.key)
                                    }
                                }
                            )
                        }
                    }
                    item {
                        FilterChip(
                            selected = false,
                            onClick = { showLanguageDialog.value = true }, //showLanguageDialog.value = true
                            label = { Text("Select other")},
                        )
                    }
                }

                //Service

                //Length

                //TMDB-Rating

                //Vote Count


            }
        }
        Box(modifier = Modifier){
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
                horizontalArrangement = Arrangement.Center)  {
                TextButton(onClick = {
                    discoverOptions.value = DiscoverOptions()
                }) {
                    Text("Reset")
                }
                Spacer(modifier = Modifier.padding(start = 32.dp, end = 32.dp))
                Button(onClick = {
                    discoverViewModel.loadDiscoveredMovies(discoverOptions.value) }) {
                    Text("Discover Movies")
                }
            }
        }
    }
    DiscoverLanguageDialog(showLanguageDialog, discoverOptions)
}