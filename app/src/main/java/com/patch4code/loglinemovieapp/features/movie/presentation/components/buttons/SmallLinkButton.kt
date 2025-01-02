package com.patch4code.loglinemovieapp.features.movie.presentation.components.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * SmallLinkButton - composable function for displaying a small, styled button that opens a URL link.
 *
 * @author Patch4Code
 */
@Composable
fun SmallLinkButton(name: String, url: String, enabled: Boolean, uriHandler: UriHandler){
    FilledTonalButton(
        onClick = { uriHandler.openUri(url) },
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
        modifier = Modifier
            .sizeIn(minWidth = 64.dp, minHeight = 24.dp)
            .padding(end = 8.dp),
        enabled = enabled
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleSmall.copy(fontSize = 12.sp)
        )
    }
}