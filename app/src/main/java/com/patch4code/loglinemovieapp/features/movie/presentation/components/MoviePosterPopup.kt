package com.patch4code.loglinemovieapp.features.movie.presentation.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.presentation.utils.MovieHelper
import com.patch4code.loglinemovieapp.features.movie.domain.model.MovieDetails
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MoviePosterPopup - Composable function that displays a popup with the movie poster.
 *
 * @author Patch4Code
 */
@Composable
fun MoviePosterPopup(openPosterPopup: Boolean, movieDetails: MovieDetails?, onPosterPopupClose:() -> Unit){

    if(openPosterPopup){

        // State variables to control the visibility of the close button and track touch events
        var isButtonVisible by remember { mutableStateOf(true) }
        var touchTime by remember { mutableLongStateOf(System.currentTimeMillis()) }

        // Animate the close button based on its visibility state
        val translateY by animateFloatAsState(
            targetValue = if(isButtonVisible) 0f else -200f,
            animationSpec = tween(durationMillis = 1500),
            label = ""
        )

        // Handle back button press to close the popup
        BackHandler {
            onPosterPopupClose()
        }

        // Effect to automatically hide the close button after a delay
        LaunchedEffect(isButtonVisible) {
            if (isButtonVisible) {
                delay(4000)
                if (System.currentTimeMillis() - touchTime >= 4000) {
                    isButtonVisible = false
                }
            }
        }

        // Surface containing the poster image and close button
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .pointerInput(Unit) {
                    detectTapGestures {
                        touchTime = System.currentTimeMillis()
                        isButtonVisible = true
                    }
                }
                .zIndex(2f)
        ) {
            val movieTitle = movieDetails?.title ?: "N/A"
            val moviePosterUrl: String = MovieHelper.processPosterUrl(movieDetails?.posterPath)

            Box {
                AsyncImage(
                    model = moviePosterUrl,
                    contentDescription = "$movieTitle${stringResource(id = R.string.poster_description_appendage)}",
                    modifier = Modifier.fillMaxWidth(),
                    error = painterResource(id = R.drawable.movie_poster_placeholder)
                )
                // Close button aligned to the top right corner of the surface
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    FilledTonalIconButton(
                        onClick = { onPosterPopupClose() },
                        modifier= Modifier
                            .padding(top = 4.dp, end = 8.dp)
                            .offset { IntOffset(0, translateY.roundToInt()) }
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    }
                }
            }
        }
    }
}