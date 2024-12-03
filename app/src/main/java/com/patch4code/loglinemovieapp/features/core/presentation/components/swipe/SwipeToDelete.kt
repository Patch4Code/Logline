package com.patch4code.loglinemovieapp.features.core.presentation.components.swipe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    isDeleting: Boolean,
    onDelete: () -> Unit,
    content: @Composable (T, Boolean) -> Unit
){

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {dismissValue ->
            if(dismissValue == SwipeToDismissBoxValue.EndToStart){
                onDelete()
            }
            false
        },
        // positional threshold of 25%
        positionalThreshold = { it * .25f }
    )

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = { DismissBackground(dismissState)},
        content = {  content(item, isDeleting) },
        enableDismissFromStartToEnd = false
    )
}

@Composable
fun DismissBackground(dismissState: SwipeToDismissBoxState) {

    val color = when (dismissState.dismissDirection) {
        SwipeToDismissBoxValue.EndToStart -> Color.Gray
        else -> Color.Transparent
    }
    val iconColor = when (dismissState.dismissDirection) {
        SwipeToDismissBoxValue.EndToStart -> Color.White
        else -> Color.Transparent
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(16.dp),
        contentAlignment = Alignment.CenterEnd
    ){
        Icon(imageVector = Icons.Default.Delete, contentDescription = null, tint = iconColor)
    }
}