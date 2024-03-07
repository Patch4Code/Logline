package com.moritz.movieappuitest.features.reviews.presentation.screen_reviews

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.moritz.movieappuitest.R
import com.moritz.movieappuitest.features.core.presentation.utils.JSONHelper
import com.moritz.movieappuitest.features.core.presentation.utils.MovieHelper
import com.moritz.movieappuitest.features.diary.domain.model.LoggedMovie
import com.moritz.movieappuitest.features.navigation.domain.model.Screen
import com.moritz.movieappuitest.features.navigation.presentation.screen_navigation.NavigationViewModel
import java.net.URLDecoder

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReviewDetailsView(navController: NavController, navViewModel: NavigationViewModel, loggedElement: String?){

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.ReviewDetailScreen)
    }

    val decodedLoggedElementString = URLDecoder.decode(loggedElement, "UTF-8")
    val loggedElementData: LoggedMovie = JSONHelper.fromJson(decodedLoggedElementString)

    val movieTitle = loggedElementData.movie.title
    val moviePosterUrl: String = MovieHelper.processPosterUrl(loggedElementData.movie.posterUrl)
    val movieYear = MovieHelper.extractYear(loggedElementData.movie.releaseDate)
    val rating = loggedElementData.rating.toString()
    val formatedDate = MovieHelper.formatDate(loggedElementData.date)
    val review = loggedElementData.review

    LazyColumn (modifier = Modifier.padding(16.dp)){
        item {
            Row{
                Card (modifier = Modifier
                    .height(200.dp)
                    .width(133.dp)
                    .clickable { },
                    colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
                ) {
                    AsyncImage(
                        model = moviePosterUrl,
                        contentDescription = "$movieTitle-Poster",
                        error = painterResource(id = R.drawable.movie_poster_placeholder)
                    )
                }
                Column (modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)){
                    Text(text = movieTitle, style = MaterialTheme.typography.titleMedium, maxLines = 2)
                    Text(text = movieYear, style = MaterialTheme.typography.bodyMedium)
                    Row (
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Bottom
                    ){
                        Text(text = rating, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.align(Alignment.CenterVertically))
                        Icon(
                            imageVector = Icons.Default.StarRate,
                            contentDescription = "StarRate",
                            tint = Color.Yellow,
                            modifier = Modifier
                                .size(15.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }
                    Text(text = "${formatedDate[0]}. ${formatedDate[1]} ${formatedDate[2]}", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.padding(8.dp))
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Review")
                    }

                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = review, style = MaterialTheme.typography.bodyMedium)
        }
    }
}