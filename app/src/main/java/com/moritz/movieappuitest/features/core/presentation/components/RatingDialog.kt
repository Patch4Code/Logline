package com.moritz.movieappuitest.features.core.presentation.components

import android.view.MotionEvent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DiaryEditRatingDialog(rating: Int, openRatingDialog: Boolean, onAccept:(rating: Int) ->Unit, onCancel: () ->Unit){

    if(openRatingDialog){

        var movieRating by remember { mutableStateOf(rating) }

        AlertDialog(
            onDismissRequest = { onCancel() }, //openRatingDialog.value = false
            title = {
                Row {
                    Text(text = "Change Rating", modifier = Modifier.weight(1f))
                    Text(text = movieRating.toString(),  fontSize = 40.sp, style = MaterialTheme.typography.headlineLarge )
                }

                Spacer(modifier = Modifier.padding(32.dp))
            },
            text = {
                Row (verticalAlignment = Alignment.CenterVertically){
                    for(index in 1 .. 10){
                        Icon(
                            modifier = Modifier
                                .pointerInteropFilter {
                                    when (it.action) {
                                        MotionEvent.ACTION_DOWN -> {
                                            movieRating = index
                                        }
                                    }
                                    true
                                }
                                .size(27.dp),
                            imageVector = if(index <= movieRating){
                                Icons.Default.StarRate}else Icons.Default.StarOutline,
                            contentDescription = null,
                            tint = Color.Yellow)
                    }
                }
                Spacer(modifier = Modifier.padding(32.dp))
            },
            confirmButton = {
                Button(onClick = { onAccept(movieRating) } //openRatingDialog.value = false
                ) {
                    Text(text = "OK")
                }
            },
            dismissButton = {
                Button(onClick = { onCancel() }) {//openRatingDialog.value = false
                    Text(text = "Cancel")
                }
            },
            modifier = Modifier
                .padding(0.dp)
                .width(500.dp),
        )
    }
}