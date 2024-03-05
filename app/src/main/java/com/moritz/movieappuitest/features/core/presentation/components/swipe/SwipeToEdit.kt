package com.moritz.movieappuitest.features.core.presentation.components.swipe

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
import androidx.compose.material.icons.filled.Edit
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
import kotlinx.coroutines.delay

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
