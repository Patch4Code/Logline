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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieGenres
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieLanguages
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieYears
import com.patch4code.loglinemovieapp.features.search.domain.model.DiscoverOptions
import com.patch4code.loglinemovieapp.features.search.presentation.screen_search.DiscoverViewModel

@Composable
fun DiscoverOptionSelection(
    discoverViewModel: DiscoverViewModel,
    discoverOptions: MutableState<DiscoverOptions>
){

    val discoverSortOptions = ""
    val availableGenres:  Map<Int, String> = MovieGenres.getAllGenres()

    val decades = MovieYears.getDecades()
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

            }
        }
        Box(modifier = Modifier){
            Row(modifier = Modifier.fillMaxWidth().padding(20.dp),
                horizontalArrangement = Arrangement.Center)  {
                TextButton(onClick = {}) {
                    Text("Reset")
                }
                Spacer(modifier = Modifier.padding(start = 32.dp, end = 32.dp))
                Button(onClick = { discoverViewModel.loadDiscoveredMovies(discoverOptions.value) }) {
                    Text("Discover Movies")
                }
            }
        }
    }
}