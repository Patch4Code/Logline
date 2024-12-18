package com.patch4code.loglinemovieapp.features.navigation.presentation.components.topbar_providers

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.TopBarViewModel

@Composable
fun ProvideTopBarSearchViewTabs(selectedTabIndex: MutableIntState){

    val tabItems = listOf("Search", "Discover")

    val viewModelStoreOwner = LocalViewModelStoreOwner.current
    (viewModelStoreOwner as? NavBackStackEntry)?.let { owner ->
        val viewModel: TopBarViewModel = viewModel(
            viewModelStoreOwner = owner,
            initializer = { TopBarViewModel() },
        )
        LaunchedEffect(Unit) {
            viewModel.actions = {
                TabRow(selectedTabIndex = selectedTabIndex.intValue,
                    modifier = Modifier.fillMaxWidth().fillMaxHeight()
                    ) {
                    tabItems.forEachIndexed {index, item->
                        Tab(
                            selected = index == selectedTabIndex.intValue,
                            onClick = { selectedTabIndex.intValue = index },
                            text = { Text(text = item) }
                        )
                    }
                }
            }
        }
    }
}