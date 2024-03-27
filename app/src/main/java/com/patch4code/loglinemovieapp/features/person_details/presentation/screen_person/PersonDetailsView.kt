package com.patch4code.loglinemovieapp.features.person_details.presentation.screen_person

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.presentation.components.ExpandableText
import com.patch4code.loglinemovieapp.features.core.presentation.utils.MovieHelper
import com.patch4code.loglinemovieapp.features.home.presentation.components.MovieHomeBrowseCard
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel

@Composable
fun PersonDetailsView(
    navController: NavController,
    personDetailsViewModel: PersonDetailsViewModel = viewModel(),
    navViewModel: NavigationViewModel,
    id: Int?
){

    val personId = id?: -1

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.PersonDetailsScreen)
        personDetailsViewModel.loadPersonDetails(personId)
    }

    val personDetails = personDetailsViewModel.personDetails.observeAsState().value

    LazyColumn(modifier = Modifier.padding(16.dp)){
        item{
            Row(modifier = Modifier.padding(bottom = 16.dp)){
                Card (modifier = Modifier
                    .height(200.dp)
                    .width(133.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
                ) {
                    AsyncImage(
                        model = MovieHelper.processPosterUrl(personDetails?.profilePath),
                        contentDescription = "Image of ${personDetails?.name}",
                        error = painterResource(id = R.drawable.movie_poster_placeholder)
                    )
                }

                Column (modifier = Modifier
                    .height(200.dp)
                    .padding(8.dp)){
                    Text(text = personDetails?.name ?: "N/A", style = MaterialTheme.typography.headlineMedium)
                    Text(text = "Known for: ${personDetails?.knownForDepartment}")
                }
            }
            Text(text = "Bio:")
            ExpandableText(text = personDetails?.biography ?: "N/A")
            Spacer(modifier = Modifier.padding(top = 16.dp))

            val homeMoviesMap : Map<String, List<Movie>> = mapOf(
                "Actor" to listOf(
                    Movie(),
                    Movie(),
                    Movie(),
                ),
                "Director" to listOf(
                    Movie(),
                    Movie(),
                    Movie(),
                )
            )

            homeMoviesMap?.forEach { (groupName, movies) ->
                Text(text = groupName,
                    modifier = Modifier.padding(top = 16.dp),
                    fontWeight = FontWeight.Bold)
                LazyRow {
                    items(movies) { movie ->
                        MovieHomeBrowseCard(navController, movie)
                    }
                }
            }
        }
    }
}