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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.features.search.domain.model.DiscoverOptions
import com.patch4code.loglinemovieapp.features.search.presentation.screen_search.DiscoverViewModel

@Composable
fun DiscoverOptionSelection(
    discoverViewModel: DiscoverViewModel,
    discoverOptions: MutableState<DiscoverOptions>
){

    Column {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .weight(1f)
        ) {
            item {
                Text("Sort by")
                DiscoverSortDropdown(discoverOptions)

                Spacer(modifier = Modifier.padding(8.dp))

                Text("Genre")
                //only Exact genre match
                DiscoverGenreSelection(discoverOptions)

                Spacer(modifier = Modifier.padding(8.dp))

                Text("Decade/Year")
                DiscoverDecadeYearSelection(discoverOptions)

                //Origin Country

                Spacer(modifier = Modifier.padding(8.dp))

                //Original Language
                Text("Original Language")
                DiscoverLanguageSection(discoverOptions)

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
}