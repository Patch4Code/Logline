package com.patch4code.loglinemovieapp.features.navigation.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * TopBar -Composable function to display the top bar.
 * Displays the title of the  current screen (from navViewModel) and provides menu icon to
 * expand the navigation drawer.
 *
 * @author Patch4Code
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavController,
    //navViewModel: NavigationViewModel,
    scrollBehavior: TopAppBarScrollBehavior,
    onDrawerStateChanged: () -> Unit
)
{
    val backStackEntry by navController.currentBackStackEntryAsState()

    backStackEntry?.let { entry ->

        val navViewModel: NavigationViewModel = viewModel(
            viewModelStoreOwner = entry,
            initializer = { NavigationViewModel() },
        )

        TopAppBar (
            title = navViewModel.title,
            navigationIcon = {
                if (navViewModel.navigationIcon == navViewModel.defaultNavigationIcon) {
                    IconButton(onClick = {onDrawerStateChanged()}) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = stringResource(id = R.string.menu_icon_description))
                    }
                }
                else { navViewModel.navigationIcon() }
            },
            actions = navViewModel.actions,
            scrollBehavior = scrollBehavior
        )
    }
}

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