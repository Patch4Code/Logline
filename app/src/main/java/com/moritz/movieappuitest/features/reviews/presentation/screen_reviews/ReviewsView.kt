package com.moritz.movieappuitest.features.reviews.presentation.screen_reviews

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.moritz.movieappuitest.R
import com.moritz.movieappuitest.features.core.presentation.utils.JSONHelper.toJson
import com.moritz.movieappuitest.features.core.presentation.utils.MovieHelper
import com.moritz.movieappuitest.features.navigation.domain.model.Screen
import com.moritz.movieappuitest.features.navigation.presentation.screen_navigation.NavigationViewModel
import java.net.URLEncoder

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ReviewsView(navController: NavController, navViewModel: NavigationViewModel, reviewsViewModel: ReviewsViewModel = viewModel()){

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.ReviewsScreen)
    }

    val reviewedLogs = reviewsViewModel.reviewedLogs.observeAsState().value


    LazyColumn(modifier = Modifier.padding(16.dp)){
        reviewedLogs?.forEach { loggedItem ->
            item {
                val jsonLoggedItem = loggedItem.toJson()
                val encodedJsonLoggedItem = URLEncoder.encode(jsonLoggedItem, "UTF-8")

                Column (modifier = Modifier
                    .fillMaxSize()
                    .clickable { navController.navigate(Screen.ReviewDetailScreen.withArgs(encodedJsonLoggedItem)) }
                ){

                    FlowRow (modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                    ){
                        Text(
                            text = "${loggedItem.movie.title} (${MovieHelper.extractYear(loggedItem.movie.releaseDate)})",
                            style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.padding(start = 8.dp))
                        Row{
                            Icon(
                                imageVector = Icons.Default.StarRate,
                                contentDescription = "StarRate",
                                tint = Color.Yellow,
                                modifier = Modifier
                                    .size(15.dp)
                                    .align(Alignment.CenterVertically)
                            )
                            Text(text = "${loggedItem.rating}", style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.align(Alignment.CenterVertically))
                        }
                    }

                    Row (modifier = Modifier.height(150.dp)){
                        AsyncImage(
                            model = MovieHelper.processPosterUrl(loggedItem.movie.posterUrl),
                            contentDescription = "Poster",
                            error = painterResource(id = R.drawable.movie_poster_placeholder)
                        )

                        Spacer(modifier = Modifier.padding(8.dp))
                        Text(
                            text = loggedItem.review,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 7,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    HorizontalDivider(modifier = Modifier.padding(top = 16.dp))
                }
            }
        }
    }
}