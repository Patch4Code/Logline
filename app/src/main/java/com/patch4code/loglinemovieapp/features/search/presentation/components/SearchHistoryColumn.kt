package com.patch4code.loglinemovieapp.features.search.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.features.search.presentation.screen_search.SearchViewModel

@Composable
fun SearchHistoryColumn(
    searchViewModel: SearchViewModel,
    onSearchHistoryClick:(itemString: String)->Unit
){

    LaunchedEffect(Unit) {
        searchViewModel.loadSearchHistory()
    }
    val searchHistory = searchViewModel.searchHistory.observeAsState().value

    val openClearHistoryDialog = remember { mutableStateOf(false) }

    if(searchHistory.isNullOrEmpty()) return

    LazyColumn(modifier = Modifier.padding(start = 18.dp, end = 12.dp)) {

        item {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text("Search History", modifier = Modifier.weight(1f), style = MaterialTheme.typography.titleMedium)
                TextButton(onClick = { openClearHistoryDialog.value = true }) {
                    Text("Clear")
                }
            }
            HorizontalDivider()
        }
        item {
            searchHistory.forEach {searchHistoryItem->

                Row(modifier = Modifier
                        .fillMaxWidth()
                        .clickable{ onSearchHistoryClick(searchHistoryItem.query) },
                    verticalAlignment = Alignment.CenterVertically
                ){

                    Icon(imageVector = Icons.Default.History, contentDescription = null)
                    Spacer(modifier = Modifier.padding(8.dp))

                    Text(searchHistoryItem.query, modifier = Modifier.weight(1f), maxLines = 1, overflow = TextOverflow.Ellipsis)

                    IconButton(onClick = { searchViewModel.deleteItemFromSearchHistory(searchHistoryItem) }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    }
                }
            }
        }
    }
    ClearHistoryDialog(searchViewModel, openClearHistoryDialog)
}