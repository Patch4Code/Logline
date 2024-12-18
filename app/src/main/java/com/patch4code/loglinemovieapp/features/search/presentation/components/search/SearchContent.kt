package com.patch4code.loglinemovieapp.features.search.presentation.components.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.core.presentation.components.LoadErrorDisplay
import com.patch4code.loglinemovieapp.features.core.presentation.components.LoadingIndicator
import com.patch4code.loglinemovieapp.features.search.presentation.components.search.history.SearchHistoryColumn
import com.patch4code.loglinemovieapp.features.search.presentation.components.search.results.NoSearchResultText
import com.patch4code.loglinemovieapp.features.search.presentation.components.search.results.SearchResultsList
import com.patch4code.loglinemovieapp.features.search.presentation.components.utils.TextInputSaver
import com.patch4code.loglinemovieapp.features.search.presentation.screen_search.SearchViewModel
import com.patch4code.loglinemovieapp.features.search.presentation.screen_search.SearchViewModelFactory
import com.patch4code.loglinemovieapp.features.search.presentation.utils.HandleClearSearch
import com.patch4code.loglinemovieapp.features.search.presentation.utils.HandleFocusRequest
import com.patch4code.loglinemovieapp.features.search.presentation.utils.SearchExtensions.triggerSearch
import com.patch4code.loglinemovieapp.room_database.LoglineDatabase


@Composable
fun SearchContent(
    navController: NavController,
    searchFocusRequest: MutableState<Boolean>,
    db: LoglineDatabase,
    selectedTabIndex: Int,
    searchViewModel: SearchViewModel = viewModel(
        factory = SearchViewModelFactory(db.searchHistoryDao)
    )
){

    val textInput = rememberSaveable(stateSaver = TextInputSaver.saver) { mutableStateOf(TextFieldValue("")) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val isLoading by searchViewModel.isLoading.observeAsState(initial = false)
    val hasLoadError by searchViewModel.hasLoadError.observeAsState(initial = false)
    val searchResult = searchViewModel.searchedMovies.observeAsState().value
    val listState = rememberSaveable(saver = LazyListState.Saver) { LazyListState() }


    if(selectedTabIndex != 0) return

    HandleFocusRequest(searchFocusRequest, focusRequester, keyboardController)
    HandleClearSearch(textInput, searchViewModel)

    Column {
        SearchBar(
            textInput = textInput,
            focusRequester = focusRequester,
            focusManager = focusManager,
            onSearch = {query ->
                triggerSearch(query, keyboardController, focusManager, searchViewModel)
            }
        )
        Spacer(modifier = Modifier.padding(4.dp))

        when {
            isLoading -> LoadingIndicator()
            hasLoadError -> LoadErrorDisplay(onReload = { searchViewModel.searchMovie(textInput.value.text) })
            searchResult == null -> SearchHistoryColumn(searchViewModel) { itemString->
                textInput.value = TextFieldValue(itemString, TextRange(itemString.length))
                triggerSearch(itemString, keyboardController, focusManager, searchViewModel)
            }
            searchResult.isEmpty() -> NoSearchResultText()
            else -> SearchResultsList(searchResult, navController, listState){
                searchViewModel.loadMoreMovies()
            }
        }
    }
}
