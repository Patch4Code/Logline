package com.patch4code.loglinemovieapp.features.profile.presentation.components.profile_edit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.presentation.utils.TmdbCredentials
import com.patch4code.loglinemovieapp.features.profile.domain.model.UserProfile

@Composable
fun ProfileEditFavMoviesSection(userProfile:UserProfile?){

    HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))

    Text(text = "Favourite Movies", style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(8.dp))
    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp, bottom = 8.dp),
    ){
        userProfile?.favouriteMovies?.forEach { movie->
            val movieId = movie.id
            val moviePosterUrl = TmdbCredentials.POSTER_URL + movie.posterUrl
            AsyncImage(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clickable {
                        if(movieId >= 0){

                        } else{
                            //open dialog to select movie
                        }
                    },
                model = moviePosterUrl,
                contentDescription = movie.title,
                error = painterResource(id = R.drawable.add_favourite_movie)
            )
            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}