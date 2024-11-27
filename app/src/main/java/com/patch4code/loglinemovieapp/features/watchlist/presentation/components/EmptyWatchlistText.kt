package com.patch4code.loglinemovieapp.features.watchlist.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * EmptyWatchlistText - Composable function that displays a message when the watchlist is empty.
 *
 * @author Patch4Code
 */
@Composable
fun EmptyWatchlistText(){

    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = "Your watchlist is currently empty.",
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}