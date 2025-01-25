package com.patch4code.logline.features.navigation.presentation.components.topbar_providers

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import com.patch4code.logline.R
import com.patch4code.logline.features.navigation.presentation.screen_navigation.TopBarViewModel

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ProvideTopBarSearchViewTabs - Composable function for
 * displaying top bar tabs with search and discover options.
 *
 * @author Patch4Code
 */
@Composable
fun ProvideTopBarSearchViewTabs(selectedTabIndex: MutableIntState){

    val tabItems = listOf(stringResource(id = R.string.search_text), stringResource(id = R.string.discover_text))

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