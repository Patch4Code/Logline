package com.patch4code.loglinemovieapp.features.movie.presentation.components.header

import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.PlaylistAdd
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.WatchLater
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.presentation.components.DiaryEditRatingDialog
import com.patch4code.loglinemovieapp.features.movie.domain.model.MovieDetails
import com.patch4code.loglinemovieapp.features.movie.presentation.components.dialogs.AddToListDialog
import com.patch4code.loglinemovieapp.features.movie.presentation.screen_movie.MovieViewModel
import com.patch4code.loglinemovieapp.room_database.LoglineDatabase
import com.patch4code.loglinemovieapp.ui.theme.LightBlue

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MovieHeaderToolbar - Composable function that displays the toolbar section in the movie header.
 * Provides functionality of options for adding/editing rating, adding to watchlist, and adding to a list.
 *
 * @author Patch4Code
 */
@Composable
fun MovieHeaderToolbar(movieDetails: MovieDetails?, movieViewModel: MovieViewModel, db: LoglineDatabase){

    val openRatingDialog = remember { mutableStateOf(false)  }
    val openAddToListDialog = remember { mutableStateOf(false)  }

    val rating = movieViewModel.myRating.observeAsState().value
    val onWatchlist = movieViewModel.onWatchlist.observeAsState().value

    val context = LocalContext.current
    val toastTextAddToWatchlist = stringResource(id = R.string.watchlist_add_toast)
    val toastTextRemoveFromWatchlist = stringResource(id = R.string.watchlist_remove_toast)

    //Rating, Watchlist and addToList
    Spacer(modifier = Modifier.padding(8.dp))
    Row{
        //Rating and Watch button
        TextButton(onClick = { openRatingDialog.value = true }
        ) {
            if((rating ?: -1) > 0){
                Text(text = "$rating", style = MaterialTheme.typography.headlineMedium)
                Icon(
                    imageVector = Icons.Default.StarRate,
                    contentDescription = null,
                    tint = Color.Yellow,
                    modifier = Modifier.size(30.dp)
                )
            }
            else if(rating == 0){
                Icon(imageVector = Icons.Filled.Visibility, contentDescription = null, modifier = Modifier.size(30.dp))
            }else{
                Icon(imageVector = Icons.Outlined.Visibility, tint = Color.DarkGray, contentDescription = null, modifier = Modifier.size(30.dp))
            }
        }

        //Watchlist Button
        IconButton(
            onClick = {
                Toast.makeText(context, if(onWatchlist == false) toastTextAddToWatchlist else toastTextRemoveFromWatchlist, Toast.LENGTH_LONG).show()
                movieViewModel.changeOnWatchlist(!(onWatchlist ?: false))
            }
        ){
            Icon(imageVector = if (onWatchlist == true) Icons.Default.WatchLater else Icons.Outlined.WatchLater,
                contentDescription = null,
                tint = if (onWatchlist == true) LightBlue else Color.DarkGray,
                modifier = Modifier.size(30.dp)
            )
        }

        //Add to List Button
        IconButton(onClick = { openAddToListDialog.value = true }) {
            Icon(imageVector = Icons.AutoMirrored.Filled.PlaylistAdd,
                contentDescription = stringResource(id = R.string.add_to_list_text),
                modifier = Modifier.size(30.dp)
            )
        }

        DiaryEditRatingDialog(
            rating = rating ?: 0,
            hasDeleteButton = true,
            openRatingDialog = openRatingDialog.value,
            onAccept = { newRating->
                openRatingDialog.value = false
                movieViewModel.changeRating(newRating)
            },
            onCancel = {openRatingDialog.value = false},
            onDelete = {openRatingDialog.value = false
                movieViewModel.changeRating(-1)
            }
        )

        AddToListDialog(openAddToListDialog, movieDetails, db)
    }
}