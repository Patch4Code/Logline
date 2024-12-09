package com.patch4code.loglinemovieapp.features.search.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.search.presentation.screen_search.SearchViewModel


@Composable
fun SearchContent(
    navController: NavController,
    searchViewModel: SearchViewModel,
    searchFocusRequest: MutableState<Boolean>,
    selectedTabIndex: Int
){

    val textInput = remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current


    val searchResult = searchViewModel.searchedMovies.observeAsState().value

    LaunchedEffect(searchFocusRequest.value) {
        if (searchFocusRequest.value && selectedTabIndex == 0) {
            focusRequester.requestFocus()
            keyboardController?.show()
            searchFocusRequest.value = false
        }
    }

    Column {
        Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.Center
        ) {
            // text-field for input of a movie name
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f).padding(start = 16.dp)
                    .focusRequester(focusRequester),
                value = textInput.value,
                onValueChange = {textInput.value = it},
                label = { Text(text = stringResource(id = R.string.search_text_field_label)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    // launches search onSearch keyboardActions
                    onSearch = {
                        if(textInput.value.isNotBlank()){
                            keyboardController?.hide()
                            searchViewModel.searchMovie(textInput.value)
                        }
                    }
                )
            )
            // search button that launches the search
            IconButton(
                onClick = {
                    if(textInput.value.isNotBlank()){
                        keyboardController?.hide()
                        searchViewModel.searchMovie(textInput.value)
                    }
                },
                modifier = Modifier.align(Alignment.CenterVertically)) {
                Icon(imageVector = Icons.Default.Search, contentDescription = stringResource(id = R.string.search_icon_description))
            }
            Spacer(modifier = Modifier.padding(end = 18.dp))
        }
        Spacer(modifier = Modifier.padding(4.dp))
        // lazy column that displays the list of results represented by MovieSearchCards
        LazyColumn {
            searchResult?.forEachIndexed { index, movie->
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