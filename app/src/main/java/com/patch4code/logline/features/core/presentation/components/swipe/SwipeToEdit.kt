package com.patch4code.logline.features.core.presentation.components.swipe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
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

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * SwipeToEditContainer - Composable function for swipe-to-edit functionality
 *
 * @author Patch4Code
 */
@Composable
fun <T> SwipeToEditContainer(
    item: T,
    onEdit: () -> Unit,
    animationDuration: Int = 250,
    content: @Composable (T) -> Unit
) {
    var editActivated by remember { mutableStateOf(false) }
    var stateToMaintain by remember { mutableStateOf<SwipeToDismissBoxValue?>(null) }

    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                editActivated = true
                stateToMaintain = dismissValue
            }
            false
        }
    )

    LaunchedEffect(stateToMaintain) {
        stateToMaintain?.let {
            state.snapTo(it)
            stateToMaintain = null
        }
    }

    LaunchedEffect(editActivated) {
        if (editActivated) {
            delay(animationDuration.toLong())
            onEdit()
            delay(animationDuration.toLong() * 2)
            editActivated = false
        } else {
            state.reset()
        }
    }

    SwipeToDismissBox(
        state = state,
        backgroundContent = { DismissEditBackground(state) },
        content = { content(item) },
        enableDismissFromStartToEnd = false
    )
}

@Composable
fun DismissEditBackground(dismissState: SwipeToDismissBoxState) {

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
        Icon(imageVector = Icons.Default.Edit, contentDescription = null, tint = iconColor)
    }
}