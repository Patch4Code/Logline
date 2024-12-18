package com.patch4code.loglinemovieapp.features.search.presentation.components.discover

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
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

    val chipWidth = 72.dp

    val discoverSortOptions = DiscoverSortOptions.entries.toList()

    val availableGenres:  Map<Int, String> = MovieGenres.getAllGenres()


    val discoverDecades = MovieYears.getDiscoverDecades()
    val years = MovieYears.getYears()

    val originCountries = ""
    val originalLanguages = MovieLanguages.getPrimaryLanguages()

    //Services (load from API)

    //length

    //tmdb rating

    //vote count

    //peron and company

    Column {

        LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp).weight(1f)) {
            item {
                Text("Sort by")
                DiscoverSortDropdown(discoverOptions, discoverSortOptions)

                Spacer(modifier = Modifier.padding(8.dp))

                Text("Genre")

                //only Exact genre match

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        FilterChip(
                            selected = discoverOptions.value.genres.isEmpty(),
                            onClick = {
                                discoverOptions.value = discoverOptions.value.copy(genres = emptyList())
                            },
                            label = { Text("Any Genre")},
                        )
                    }
                    availableGenres.forEach{genreMap ->
                        item {
                            val hasGenre = discoverOptions.value.genres.contains(genreMap.key)
                            FilterChip(
                                label = {Text(genreMap.value)},
                                selected = hasGenre,
                                onClick = {
                                    val updatedGenres = discoverOptions.value.genres.toMutableList()
                                    if (hasGenre) {
                                        updatedGenres.remove(genreMap.key)
                                    } else {
                                        updatedGenres.add(genreMap.key)
                                    }
                                    discoverOptions.value = discoverOptions.value.copy(genres = updatedGenres)
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.padding(8.dp))
                Text("Decade/Year")

                FilterChip(
                    selected = discoverOptions.value.primaryReleaseYear == null &&
                            discoverOptions.value.primaryReleaseDateGte == null &&
                            discoverOptions.value.primaryReleaseDateLte == null,
                    onClick = {
                        discoverOptions.value = discoverOptions.value.clearPrimaryRelease()
                    },
                    label = { Text("Any Year") }
                )
                Spacer(modifier = Modifier.padding(4.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    discoverDecades.forEach{discoverDecade->
                        item {
                            FilterChip(
                                modifier = Modifier.width(chipWidth),
                                selected = discoverOptions.value.primaryReleaseDateGte == discoverDecade.decadeStartDate &&
                                        discoverOptions.value.primaryReleaseDateLte == discoverDecade.decadeEndDate,
                                onClick = {
                                    if (discoverOptions.value.primaryReleaseDateGte == discoverDecade.decadeStartDate &&
                                        discoverOptions.value.primaryReleaseDateLte == discoverDecade.decadeEndDate
                                    ) {
                                        discoverOptions.value = discoverOptions.value.clearPrimaryRelease()
                                    } else {
                                        discoverOptions.value = discoverOptions.value.copy(
                                            primaryReleaseDateGte = discoverDecade.decadeStartDate,
                                            primaryReleaseDateLte = discoverDecade.decadeEndDate
                                        )
                                    }
                                },
                                label = { Text(text = discoverDecade.decade, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(4.dp))


            }
        }
        Box(modifier = Modifier){
            Row(modifier = Modifier.fillMaxWidth().padding(20.dp),
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
}