package com.patch4code.logline.features.search.presentation.components.search.history

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.patch4code.logline.features.search.presentation.screen_search.SearchViewModel

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * SearchHistoryColumn - Composable for displaying a column of search history items.
 *
 * @author Patch4Code
 */
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
        // Header
        item {
            SearchHistoryHeader{ openClearHistoryDialog.value = true }
        }
        // Search-History Elements
        item {
            searchHistory.forEach {searchHistoryItem->
                SearchHistoryItem(
                    searchHistoryItem = searchHistoryItem,
                    onItemClick = { onSearchHistoryClick(searchHistoryItem.query) },
                    onDeleteClick = { searchViewModel.deleteItemFromSearchHistory(searchHistoryItem) }
                )
            }
        }
    }
    ClearHistoryDialog(searchViewModel, openClearHistoryDialog)
}