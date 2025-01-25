package com.patch4code.logline.features.core.presentation.components

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

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ExpandableText - Composable function for displaying expandable text
 *
 * @author Patch4Code
 */
@Composable
fun ExpandableText(text: String, maxLinesCollapsed: Int = 2) {

    // State to track whether to show more text or not
    var showMore by remember { mutableStateOf(false) }

    Column (modifier = Modifier.padding(top = 8.dp)){
        Column(modifier = Modifier
            .animateContentSize(animationSpec = tween(100))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { showMore = !showMore }) {

            // Render text with the option to expand or collapse based on the showMore state
            if (showMore) {
                Text(text = text, style = MaterialTheme.typography.bodyMedium)
            } else {
                Text(text = text, style = MaterialTheme.typography.bodyMedium, maxLines = maxLinesCollapsed, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}