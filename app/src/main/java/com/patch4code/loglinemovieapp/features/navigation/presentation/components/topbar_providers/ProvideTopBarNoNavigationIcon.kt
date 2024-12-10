package com.patch4code.loglinemovieapp.features.navigation.presentation.components.topbar_providers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.TopBarViewModel

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ProvideTopBarNoNavigationIcon - Composable function that removes the navigation icon from the top app bar.
 *
 * @author Patch4Code
 */
@Composable
fun ProvideTopBarNoNavigationIcon() {
    val viewModelStoreOwner = LocalViewModelStoreOwner.current
    (viewModelStoreOwner as? NavBackStackEntry)?.let { owner ->
        val viewModel: TopBarViewModel = viewModel(
            viewModelStoreOwner = owner,
            initializer = { TopBarViewModel() },
        )
        LaunchedEffect(null) {
            viewModel.navigationIcon = {}
        }
    }
}