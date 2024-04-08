package com.patch4code.loglinemovieapp.features.movie.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.patch4code.loglinemovieapp.features.core.presentation.utils.TmdbCredentials
import com.patch4code.loglinemovieapp.features.movie.domain.model.CountryProviders


@Composable
fun MovieWatchProviders(
    movieProviders: CountryProviders?,
    watchCountry: String?,
    movieId: Int?){

    //Log.e("MovieWatchProviders", "$movieProviders")
    if (movieProviders == null) return

    val uriHandler = LocalUriHandler.current

    Row (modifier = Modifier.clickable { uriHandler.openUri("https://www.themoviedb.org/movie/$movieId/watch?locale=$watchCountry)")}){
        if (movieProviders.flatrate != null){
            Column (modifier = Modifier.padding(4.dp)) {
                Text(text = "Stream", modifier = Modifier.padding(bottom = 4.dp),style = MaterialTheme.typography.titleSmall)
                Row {
                    var maxIndex = -1
                    movieProviders.flatrate?.forEachIndexed { index, provider ->
                        maxIndex = index
                        if (index < 4){
                            AsyncImage(
                                model = TmdbCredentials.OTHER_IMAGE_URL + provider.logoPath,
                                modifier = Modifier
                                    .size(30.dp)
                                    .padding(2.dp)
                                    .clip(CircleShape),
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    if(maxIndex > 4){
                        Card (modifier = Modifier
                            .size(30.dp)
                            .padding(2.dp)
                            .clip(CircleShape),
                                colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
                        ){
                            Text(text = "+${maxIndex-4}", modifier = Modifier.align(CenterHorizontally))
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.padding(4.dp))
        if (movieProviders.rent != null){
            Column (modifier = Modifier.padding(4.dp)){
                Text(text = "Rent/Buy", modifier = Modifier.padding(bottom = 4.dp),style = MaterialTheme.typography.titleSmall)
                Row {
                    var maxIndex = -1
                    movieProviders.rent?.forEachIndexed { index, provider ->
                        maxIndex = index
                        if (index < 4){
                            AsyncImage(
                                model = TmdbCredentials.OTHER_IMAGE_URL + provider.logoPath,
                                modifier = Modifier
                                    .size(30.dp)
                                    .padding(2.dp)
                                    .clip(CircleShape),
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    if(maxIndex > 4){
                        Card (modifier = Modifier
                            .size(30.dp)
                            .padding(2.dp)
                            .clip(CircleShape),
                            colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
                        ){
                            Text(text = "+${maxIndex-4}", modifier = Modifier.align(CenterHorizontally))
                        }
                    }
                }
            }

        }
    }

    Row(modifier = Modifier.padding(start = 10.dp, top = 10.dp)){
        Text(text = "Source: ")
        Text(text = "JustWatch", fontStyle = FontStyle.Italic)
    }

    HorizontalDivider(modifier = Modifier.padding(top = 16.dp))

}