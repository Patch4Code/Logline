package com.patch4code.loglinemovieapp.features.search.presentation.components.search.history

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.patch4code.loglinemovieapp.R

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * SearchHistoryHeader - Composable for displaying the header section of the search history.
 *
 * @author Patch4Code
 */
@Composable
fun SearchHistoryHeader(onClearClick:()->Unit){
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(stringResource(id = R.string.search_history_title), modifier = Modifier.weight(1f), style = MaterialTheme.typography.titleMedium)
        TextButton(onClick = { onClearClick() })
        {
            Text(stringResource(id = R.string.clear_label))
        }
    }
    HorizontalDivider()
}