package com.patch4code.loglinemovieapp.features.movie.presentation.components.cast_and_crew

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * CreditsSectionList - Composable function that Displays a list of items (cast or crew members)
 * in a horizontal scrolling layout with a title. It supports lazy loading for large lists,
 * displaying a loading indicator when more items are being loaded.
 *
 * @author Patch4Code
 */
@Composable
fun <T> CreditsSectionList(title: String, items: List<T>, content: @Composable (T) -> Unit) {

    // If the list is empty or null, return early
    if(items.isNullOrEmpty()) return

    var maxIndex by remember { mutableStateOf(5) }
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.padding(top = 16.dp)
    ) {
        Text(text = title, fontWeight = FontWeight.Bold)
        Row(
            modifier = Modifier
                .horizontalScroll(scrollState)
                .height(IntrinsicSize.Max),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            content = {
                for (index in 0 until maxIndex.coerceAtMost(items.size)) {
                    // Render each item using the provided content composable function
                    content(items[index])
                    // If the scroll state reaches the end, trigger lazy loading
                    if (scrollState.value == scrollState.maxValue) {
                        LaunchedEffect(Unit) {
                            delay(1000) // Add delay for smooth scrolling
                            maxIndex = items.size // Set maxIndex to show all items
                        }
                    }
                }
                // Display loading indicator if more items are available
                if (maxIndex < items.size) {
                    Column(modifier = Modifier.padding(top = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .height(80.dp)
                                .width(80.dp)
                                .padding(4.dp)
                        )
                    }
                }
            }
        )
    }
}