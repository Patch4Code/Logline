package com.patch4code.loglinemovieapp.features.search.presentation.screen_search

import android.annotation.SuppressLint
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.topbar_providers.ProvideTopBarSearchViewTabs
import com.patch4code.loglinemovieapp.features.search.presentation.components.SearchContent

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * SearchView - Composable function representing the search screen view.
 * Lets the user search movies by typing in a text-field.
 *
 * @author Patch4Code
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchView(
    navController: NavController,
    searchFocusRequest: MutableState<Boolean>,
    searchViewModel: SearchViewModel = viewModel()
){
    val selectedTabIndex = remember { mutableIntStateOf(0) }

    ProvideTopBarSearchViewTabs(selectedTabIndex)

    if (selectedTabIndex.intValue == 0){
        SearchContent(navController, searchViewModel, searchFocusRequest, selectedTabIndex.intValue)
    }else if (selectedTabIndex.intValue == 1){
        Text("Empty")
    }



}