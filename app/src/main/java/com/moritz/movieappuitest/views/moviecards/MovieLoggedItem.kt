package com.moritz.movieappuitest.views.moviecards

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.moritz.movieappuitest.Screen
import com.moritz.movieappuitest.dataclasses.LoggedMovie
import com.moritz.movieappuitest.utils.MovieHelper
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieLoggedItem(navController: NavController, loggedElement: LoggedMovie) {

    val movieId = loggedElement.movie.id.toString()
    val movieTitle = loggedElement.movie.title
    val movieYear = MovieHelper.extractYear(loggedElement.movie.releaseDate)
    val moviePosterUrl = MovieHelper.processPosterUrl(loggedElement.movie.posterUrl)


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(8.dp)
            .clickable {
                navController.navigate(Screen.MovieScreen.withArgs(movieId))
            }
    ){
        val parsedDate = MovieHelper.formatDate(dateString = loggedElement.date)
        Column (modifier = Modifier.padding(end = 16.dp)){
            parsedDate[1]?.let { Text(text = it, style = MaterialTheme.typography.titleMedium) }
            parsedDate[0]?.let { Text(text = it, style = MaterialTheme.typography.headlineLarge) }
            parsedDate[2]?.let { Text(text = it, style = MaterialTheme.typography.titleSmall, color = Color.Gray) }
        }

        AsyncImage(
            model = moviePosterUrl,
            contentDescription = "${movieTitle}-Poster"
        )
        Column (modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .width(140.dp)){
            Text(text = loggedElement.movie.title, style = MaterialTheme.typography.titleMedium, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = movieYear, style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Row (
                modifier = Modifier.padding(4.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ){
                Icon(
                    imageVector = Icons.Default.StarRate,
                    contentDescription = "StarRate",
                    tint = Color.Yellow,
                    modifier = Modifier
                        .size(15.dp)
                        .align(Alignment.CenterVertically)
                )
                Text(text = "${loggedElement.rating}", color = Color.White, modifier = Modifier.align(
                    Alignment.CenterVertically
                ))
            }
        }
        Spacer(modifier = Modifier.height(4.dp))

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> swipeToEditContainer(
    item: T,
    onEdit: (T) -> Unit,
    animationDuration: Int = 500,
    content: @Composable (T) -> Unit
){
    var editActivated by remember {
        mutableStateOf(false)
    }
    val state = rememberDismissState(
        confirmStateChange = {value->
            if (value == DismissValue.DismissedToStart){
                editActivated = true
                true
            }else{
                false
            }
        }
    )
    
    LaunchedEffect(key1 = editActivated){
        if(editActivated){
            delay(animationDuration.toLong())
            onEdit(item)
        }
    }

    SwipeToDismiss(
        state = state,
        background = {
            SwipeEditBackground(swipeDismissState = state)
        },
        dismissContent = {content(item)},
        directions = setOf(DismissDirection.EndToStart)
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeEditBackground(swipeDismissState: DismissState){

    if(swipeDismissState.dismissDirection == DismissDirection.EndToStart){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
                .padding(16.dp),
            contentAlignment = Alignment.CenterEnd
        ){
            Icon(imageVector = Icons.Default.Edit, contentDescription = null, tint = Color.White)
        }
    }
}
