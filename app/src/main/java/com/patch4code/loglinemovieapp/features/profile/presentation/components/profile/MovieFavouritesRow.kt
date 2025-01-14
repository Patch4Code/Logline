package com.patch4code.loglinemovieapp.features.profile.presentation.components.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.presentation.utils.TmdbCredentials
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.profile.presentation.components.profile_edit.dialogs.SelectFavMovieDialog
import com.patch4code.loglinemovieapp.features.profile.presentation.screen_profile.ProfileViewModel

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MovieFavouriteRow - Composable function for displaying a row of favorite movies on the profile.
 * Displays 4 movie posters (non valid movies are displayed by placeholder image).
 * Posters are clickable and lead to MovieView
 *
 * @author Patch4Code
 */
@Composable
fun MovieFavouriteRow(
    navController: NavController,
    movies: List<Movie?>?,
    profileViewModel: ProfileViewModel
){

    val openSelectFavMovieDialog = remember { mutableStateOf(false)  }
    val favMovieClickedIndex = remember { mutableIntStateOf(-1 ) }

    Text(text = stringResource(id = R.string.favourite_movies_title))

    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        movies?.forEachIndexed { index, movie->
            if (movie != null){
                val movieId = movie.id
                val moviePosterUrl = TmdbCredentials.POSTER_URL + (movie.posterUrl)
                AsyncImage(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Screen.MovieScreen.withArgs(movieId.toString()))
                        },
                    model = moviePosterUrl,
                    contentDescription = movie.title,
                    error = painterResource(id = R.drawable.movie_poster_placeholder)
                )
            } else{
                Image(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .clickable {
                            favMovieClickedIndex.intValue = index
                            openSelectFavMovieDialog.value = true
                        },
                    painter = painterResource(id = R.drawable.add_favourite_movie),
                    contentDescription = null,
                )
            }

            Spacer(modifier = Modifier.padding(4.dp))
        }
    }

    SelectFavMovieDialog(openSelectFavMovieDialog, favMovieClickedIndex, profileViewModel)
}