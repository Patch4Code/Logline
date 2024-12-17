package com.patch4code.loglinemovieapp.features.search.presentation.screen_search

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.topbar_providers.ProvideTopBarSearchViewTabs
import com.patch4code.loglinemovieapp.features.search.presentation.components.DiscoverContent
import com.patch4code.loglinemovieapp.features.search.presentation.components.SearchContent
import com.patch4code.loglinemovieapp.room_database.LoglineDatabase

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * SearchDiscoverView - Composable function representing the search/discover screen view.
 * Two Tabs in TabRow. One for searching movies via text-field and the other with discover options.
 *
 * @author Patch4Code
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchDiscoverView(
    navController: NavController,
    searchFocusRequest: MutableState<Boolean>,
    db: LoglineDatabase
){
    val selectedTabIndex = remember { mutableIntStateOf(0) }

    ProvideTopBarSearchViewTabs(selectedTabIndex)

    //Tab1 with Search-UI and -functionality (UI only active when selectedTabIndex is 0)
    SearchContent(navController, searchFocusRequest, db, selectedTabIndex.intValue)

    //Tab2 with Discover-UI and -functionality (UI only active when selectedTabIndex is 1)
    DiscoverContent(navController, searchFocusRequest, selectedTabIndex.intValue)
}