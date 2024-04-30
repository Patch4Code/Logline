package com.patch4code.loglinemovieapp.features.core.presentation.components.swipe


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.DismissDirection
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.DismissState
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * swipeToDeleteContainer - Composable function for swipe-to-delete functionality
 *
 * @author Patch4Code
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> swipeToDeleteContainer(
    item: T,
    onDelete: (T) -> Unit,
    content: @Composable (T) -> Unit
){
    var isRemoved by remember {
        mutableStateOf(false)
    }
    val state = rememberDismissState(
        confirmStateChange = {value->
            if (value == DismissValue.DismissedToStart){
                isRemoved = true
                true
            }else{
                false
            }
        }
    )
    LaunchedEffect(key1 = isRemoved){
        if(isRemoved){
            onDelete(item)
        }
    }
    SwipeToDismiss(
        state = state,
        background = {
            SwipeDeleteBackground(swipeDismissState = state)
        },
        dismissContent = {content(item)},
        directions = setOf(DismissDirection.EndToStart)
    )
}

// Composable function for the background of swipe-to-delete
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeDeleteBackground(swipeDismissState: DismissState){

    val color = if (swipeDismissState.dismissDirection == DismissDirection.EndToStart) {
        Color.Gray
    } else Color.Transparent

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(16.dp),
        contentAlignment = Alignment.CenterEnd
    ){
        if (swipeDismissState.dismissDirection == DismissDirection.EndToStart) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = null, tint = Color.White)
        }
    }
}