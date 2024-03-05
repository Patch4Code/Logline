package com.moritz.movieappuitest.features.core.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun ExpandableText(text: String, maxLinesCollapsed: Int = 2) {

    var showMore by remember { mutableStateOf(false) }

    Column (modifier = Modifier.padding(top = 8.dp)){
        Column(modifier = Modifier
            .animateContentSize(animationSpec = tween(100))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { showMore = !showMore }) {

            if (showMore) {
                Text(text = text, style = MaterialTheme.typography.bodyMedium)
            } else {
                Text(text = text, style = MaterialTheme.typography.bodyMedium, maxLines = maxLinesCollapsed, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}