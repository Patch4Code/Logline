package com.patch4code.loglinemovieapp.features.navigation.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel

@Composable
fun ProvideTopBarTitle(title: String) {
    val viewModelStoreOwner = LocalViewModelStoreOwner.current
    (viewModelStoreOwner as? NavBackStackEntry)?.let { owner ->
        val viewModel: NavigationViewModel = viewModel(
            viewModelStoreOwner = owner,
            initializer = { NavigationViewModel() },
        )
        LaunchedEffect(title) {
            viewModel.title = {
                Text(text = title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun ProvideTopBarBackNavigationIcon(navController: NavController) {
    val viewModelStoreOwner = LocalViewModelStoreOwner.current
    (viewModelStoreOwner as? NavBackStackEntry)?.let { owner ->
        val viewModel: NavigationViewModel = viewModel(
            viewModelStoreOwner = owner,
            initializer = { NavigationViewModel() },
        )
        LaunchedEffect(navController) {
            viewModel.navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = "Back",
                        )
                }
            }
        }
    }
}

@Composable
fun ProvideTopBarFilterActions(onClickAction: () -> Unit) {

    val viewModelStoreOwner = LocalViewModelStoreOwner.current
    (viewModelStoreOwner as? NavBackStackEntry)?.let { owner ->
        val viewModel: NavigationViewModel = viewModel(
            viewModelStoreOwner = owner,
            initializer = { NavigationViewModel() },
        )
        LaunchedEffect(onClickAction) {
            viewModel.actions = {
                IconButton(onClick = {onClickAction()}) {
                    Icon(
                        imageVector = Icons.Outlined.FilterList,
                        contentDescription = "Add",
                        tint = Color.White
                    )
                }
            }
        }
    }
}