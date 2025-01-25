package com.patch4code.logline.features.profile.presentation.components.profile

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ExpandableBio - Composable function for displaying expandable bio text for the profile.
 *
 * @author Patch4Code
 */
@Composable
fun ExpandableBio(text: String, maxLinesCollapsed: Int = 2) {

    var showMore by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(animationSpec = tween(100))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { showMore = !showMore }
        ) {
            if (showMore) {
                Text(text = text, textAlign = TextAlign.Center)
            } else {
                Text(text = text, textAlign = TextAlign.Center, maxLines = maxLinesCollapsed, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}