package com.patch4code.loglinemovieapp.features.search.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.presentation.components.LoadErrorDisplay
import com.patch4code.loglinemovieapp.features.core.presentation.components.LoadingIndicator
import com.patch4code.loglinemovieapp.features.search.presentation.screen_search.SearchViewModel
import com.patch4code.loglinemovieapp.features.search.presentation.screen_search.SearchViewModelFactory
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

    val textInput = remember { mutableStateOf(TextFieldValue("")) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val isLoading by searchViewModel.isLoading.observeAsState(initial = false)
    val hasLoadError by searchViewModel.hasLoadError.observeAsState(initial = false)
    val searchResult = searchViewModel.searchedMovies.observeAsState().value
    val listState = rememberSaveable(saver = LazyListState.Saver) { LazyListState() }



    if(selectedTabIndex != 0) return

    LaunchedEffect(searchFocusRequest.value) {
        if (searchFocusRequest.value) {
            focusRequester.requestFocus()
            keyboardController?.show()
            searchFocusRequest.value = false
        }
    }
    LaunchedEffect(textInput.value.text) {
        if(textInput.value.text.isEmpty()){
            searchViewModel.clearSearchedMovies()
        }
    }
    LaunchedEffect(Unit) {
        searchViewModel.loadSearchHistory()
    }
    val searchHistory = searchViewModel.searchHistory.observeAsState().value

    Column {
        Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.Center
        ) {
            // text-field for input of a movie name
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
                    .focusRequester(focusRequester),
                value = textInput.value,
                onValueChange = {textInput.value = it},
                label = { Text(text = stringResource(id = R.string.search_text_field_label)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if(textInput.value.text.isNotBlank()){
                            keyboardController?.hide()
                            focusManager.clearFocus()
                            searchViewModel.searchMovie(textInput.value.text)
                        }
                    }
                ),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            if(textInput.value.text.isNotBlank()){
                                keyboardController?.hide()
                                focusManager.clearFocus()
                                searchViewModel.searchMovie(textInput.value.text)
                            }
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = stringResource(id = R.string.search_icon_description)
                            )
                        }
                    )
                }
            )

        }
        Spacer(modifier = Modifier.padding(4.dp))
        // lazy column that displays the list of results represented by MovieSearchCards
        if (isLoading){
            LoadingIndicator()
        }else if(hasLoadError){
            LoadErrorDisplay(onReload = { searchViewModel.searchMovie(textInput.value.text) })
        }else if(searchResult == null){
            //here search-history could be displayed
            LazyColumn {
                item {
                    searchHistory?.forEach {searchHistoryItem->
                        Text(text = searchHistoryItem.query,
                            modifier = Modifier.clickable{ searchViewModel.deleteItemFromSearchHistory(searchHistoryItem) })
                    }
                }
            }


        }else if(searchResult.isEmpty()){
            NoSearchResultText()
        }
        else{
            LazyColumn(state = listState) {
                searchResult.forEachIndexed { index, movie->
                    item{
                        MovieSearchCard(navController, movie)

                        // load more movies, when the end of the lazy column is reached
                        if(index == searchResult.lastIndex){
                            searchViewModel.loadMoreMovies()
                        }
                    }
                }
            }
        }
    }
}