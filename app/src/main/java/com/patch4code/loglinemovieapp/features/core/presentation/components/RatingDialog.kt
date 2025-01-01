package com.patch4code.loglinemovieapp.features.core.presentation.components

import android.view.MotionEvent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.patch4code.loglinemovieapp.R

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * RatingDialog - Composable function for displaying a rating dialog.
 * Allows users to select a rating for a movie (no rating or number from 1 to 10)
 *
 * @author Patch4Code
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DiaryEditRatingDialog(
    rating: Int,
    openRatingDialog: Boolean,
    hasDeleteButton: Boolean = false,
    onAccept:(rating: Int) ->Unit,
    onCancel: () ->Unit,
    onDelete:()->Unit = {}){

    if(!openRatingDialog) return

    // State to track the selected rating
    var movieRating by remember { mutableIntStateOf(if(rating > 0) rating else 0) }

    AlertDialog(
        onDismissRequest = { onCancel() },
        title = {
            // Dialog title with selected rating text
            Row {
                Text(text = stringResource(id = R.string.change_rating_text), modifier = Modifier.weight(1f))
                if(movieRating > 0){
                    Text(text = movieRating.toString(),  fontSize = 40.sp, style = MaterialTheme.typography.headlineLarge )
                }else{
                    Column {
                        Text(
                            text = stringResource(id = R.string.no_text),
                            fontSize = 15.sp,
                            style = MaterialTheme.typography.headlineLarge.copy(lineHeight = 20.sp)
                        )
                        Text(
                            text = stringResource(id = R.string.rating_text),
                            fontSize = 15.sp,
                            style = MaterialTheme.typography.headlineLarge.copy(lineHeight = 20.sp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.padding(32.dp))
        },
        text = {
            // star icons with selected elements based on movieRating state and click functionality
            Row (verticalAlignment = Alignment.CenterVertically){
                for(index in 1 .. 10){
                    Icon(
                        modifier = Modifier
                            .pointerInteropFilter {
                                when (it.action) {
                                    MotionEvent.ACTION_DOWN -> {
                                        movieRating = if ((movieRating == 1) and (index == 1)) {
                                            0
                                        } else {
                                            index
                                        }
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
            Button(onClick = { onAccept(movieRating) }
            ) {
                Text(text = stringResource(id = R.string.ok_button_text))
            }
        },
        dismissButton = {
            Row (horizontalArrangement = Arrangement.Start){
                if(hasDeleteButton){
                    IconButton(onClick = { onDelete() }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                    }
                    Spacer(modifier = Modifier.padding(28.dp))
                }
                Button(onClick = { onCancel() }) {
                    Text(text = stringResource(id = R.string.cancel_button_text))
                }
            }
        },
        modifier = Modifier
            .padding(0.dp)
            .width(500.dp),
    )
}