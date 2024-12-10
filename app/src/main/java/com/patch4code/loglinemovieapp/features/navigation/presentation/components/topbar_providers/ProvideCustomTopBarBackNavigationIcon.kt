package com.patch4code.loglinemovieapp.features.navigation.presentation.components.topbar_providers

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.TopBarViewModel

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ProvideCustomTopBarBackNavigationIcon - Composable function that adds a back navigation icon to
 * the top app bar with customizable onClick.
 *
 * @author Patch4Code
 */
@Composable
fun ProvideCustomTopBarBackNavigationIcon(onClickAction: () -> Unit) {
    val viewModelStoreOwner = LocalViewModelStoreOwner.current
    (viewModelStoreOwner as? NavBackStackEntry)?.let { owner ->
        val viewModel: TopBarViewModel = viewModel(
            viewModelStoreOwner = owner,
            initializer = { TopBarViewModel() },
        )
        LaunchedEffect(onClickAction) {
            viewModel.navigationIcon = {
                IconButton(onClick = { onClickAction() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = "Back",
                    )
                }
            }
        }
    }
}