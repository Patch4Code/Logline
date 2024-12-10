package com.patch4code.loglinemovieapp.features.navigation.presentation.components.topbar_providers

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.TopBarViewModel

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ProvideTopBarSortActionsAndMoreVert - Composable function that adds a sort action icon
 * and a MoreVert icon to the top app bar.
 *
 * @param sortFilterOnClickAction A lambda function to execute when the sort icon is clicked.
 * @author Patch4Code
 */
@Composable
fun ProvideTopBarSortFilterActionsAndMoreVert(sortFilterOnClickAction: () -> Unit, moreVertOnClickAction: () -> Unit) {

    val viewModelStoreOwner = LocalViewModelStoreOwner.current
    (viewModelStoreOwner as? NavBackStackEntry)?.let { owner ->
        val viewModel: TopBarViewModel = viewModel(
            viewModelStoreOwner = owner,
            initializer = { TopBarViewModel() },
        )
        LaunchedEffect(sortFilterOnClickAction, moreVertOnClickAction) {
            viewModel.actions = {
                IconButton(onClick = {sortFilterOnClickAction()}) {
                    Icon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = "Add",
                        tint = Color.White
                    )
                }
                IconButton(onClick = {moreVertOnClickAction()}) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More",
                        tint = Color.White
                    )
                }
            }
        }
    }
}