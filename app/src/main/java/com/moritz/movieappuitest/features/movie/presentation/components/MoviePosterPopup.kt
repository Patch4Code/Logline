package com.moritz.movieappuitest.features.movie.presentation.components

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.moritz.movieappuitest.R
import com.moritz.movieappuitest.features.movie.domain.model.MovieDetails
import com.moritz.movieappuitest.features.core.presentation.utils.MovieHelper
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@Composable
fun MoviePosterPopup(openPosterPopup: Boolean, movieDetails: MovieDetails?, onPosterPopupClose:() -> Unit){

    if(openPosterPopup){

        var isButtonVisible by remember { mutableStateOf(true) }
        var touchTime by remember { mutableStateOf(System.currentTimeMillis()) }

        val translateY by animateFloatAsState(
            targetValue = if(isButtonVisible) 0f else -200f,
            animationSpec = tween(durationMillis = 1500),
            label = ""
        )

        BackHandler {
            onPosterPopupClose()
        }

        LaunchedEffect(isButtonVisible) {
            if (isButtonVisible) {
                delay(4000)
                if (System.currentTimeMillis() - touchTime >= 4000) {
                    isButtonVisible = false
                }
            }
        }

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
        ) {
            val movieTitle = movieDetails?.title ?: "N/A"
            val moviePosterUrl: String = MovieHelper.processPosterUrl(movieDetails?.posterPath)

            Box {
                AsyncImage(
                    model = moviePosterUrl,
                    contentDescription = "$movieTitle-Poster",
                    modifier = Modifier.fillMaxWidth(),
                    error = painterResource(id = R.drawable.movie_poster_placeholder)
                )
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