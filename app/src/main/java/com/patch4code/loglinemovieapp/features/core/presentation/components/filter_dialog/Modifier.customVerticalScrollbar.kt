package com.patch4code.loglinemovieapp.features.core.presentation.components.filter_dialog

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.customVerticalScrollbar(
    state: ScrollState,
    contentHeight: Dp,
    containerHeight: Dp,
    alwaysShowScrollbar: Boolean = false,
    width: Dp = 4.dp
): Modifier {

    val targetAlpha = if (alwaysShowScrollbar || state.isScrollInProgress) 1f else 0f
    val duration = if (state.isScrollInProgress) 150 else 500

    val alpha by animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec = tween(durationMillis = duration),
        label = ""
    )

    return drawWithContent {
        drawContent()

        // Scrollbar visibility condition
        if (state.maxValue > 0 && (alwaysShowScrollbar || alpha > 0f)) {
            val containerPx = containerHeight.toPx()
            val contentPx = contentHeight.toPx()

            // Calculate scrollbar position and size
            val proportionVisible = containerPx / contentPx
            val scrollbarHeight = proportionVisible * containerPx
            val scrollbarOffsetY = (state.value.toFloat() / state.maxValue.toFloat()) * (containerPx - scrollbarHeight)

            drawRect(
                color = Color.Gray,
                topLeft = Offset(this.size.width - width.toPx(), scrollbarOffsetY),
                size = Size(width.toPx(), scrollbarHeight),
                alpha = alpha
            )
        }
    }
}