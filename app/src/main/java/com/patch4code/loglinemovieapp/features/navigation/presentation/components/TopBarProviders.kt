package com.patch4code.loglinemovieapp.features.navigation.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Sort
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.MoreVert
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
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.TopBarViewModel

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ProvideTopBarTitle - Composable function that sets the title for the top app bar.
 *
 * @author Patch4Code
 */
@Composable
fun ProvideTopBarTitle(title: String) {
    val viewModelStoreOwner = LocalViewModelStoreOwner.current
    (viewModelStoreOwner as? NavBackStackEntry)?.let { owner ->
        val viewModel: TopBarViewModel = viewModel(
            viewModelStoreOwner = owner,
            initializer = { TopBarViewModel() },
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

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ProvideTopBarBackNavigationIcon - Composable function that adds a back navigation icon to the top app bar.
 *
 * @param navController The NavController used to manage navigation actions within the app.
 * @author Patch4Code
 */
@Composable
fun ProvideTopBarBackNavigationIcon(navController: NavController) {
    val viewModelStoreOwner = LocalViewModelStoreOwner.current
    (viewModelStoreOwner as? NavBackStackEntry)?.let { owner ->
        val viewModel: TopBarViewModel = viewModel(
            viewModelStoreOwner = owner,
            initializer = { TopBarViewModel() },
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

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ProvideTopBarSortActions - Composable function that adds a sort action icon to the top app bar.
 *
 * @param onClickAction A lambda function to execute when the sort icon is clicked.
 * @author Patch4Code
 */
@Composable
fun ProvideTopBarSortActions(onClickAction: () -> Unit) {

    val viewModelStoreOwner = LocalViewModelStoreOwner.current
    (viewModelStoreOwner as? NavBackStackEntry)?.let { owner ->
        val viewModel: TopBarViewModel = viewModel(
            viewModelStoreOwner = owner,
            initializer = { TopBarViewModel() },
        )
        LaunchedEffect(onClickAction) {
            viewModel.actions = {
                IconButton(onClick = {onClickAction()}) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.Sort,
                        contentDescription = "Add",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ProvideTopBarSortActionsAndMoreVert - Composable function that adds a sort action icon
 * and a MoreVert icon to the top app bar.
 *
 * @param sortOnClickAction A lambda function to execute when the sort icon is clicked.
 * @author Patch4Code
 */
@Composable
fun ProvideTopBarSortActionsAndMoreVert(sortOnClickAction: () -> Unit, moreVertOnClickAction: () -> Unit) {

    val viewModelStoreOwner = LocalViewModelStoreOwner.current
    (viewModelStoreOwner as? NavBackStackEntry)?.let { owner ->
        val viewModel: TopBarViewModel = viewModel(
            viewModelStoreOwner = owner,
            initializer = { TopBarViewModel() },
        )
        LaunchedEffect(sortOnClickAction, moreVertOnClickAction) {
            viewModel.actions = {
                IconButton(onClick = {sortOnClickAction()}) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.Sort,
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

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ProvideTopBarSortActionsAndFilter - Composable function that adds a sort action icon
 * and a filter icon to the top app bar.
 *
 * @param sortFilterOnClickAction A lambda function to execute when the filter icon is clicked.
 * @author Patch4Code
 */
@Composable
fun ProvideTopBarSortFilterActions(sortFilterOnClickAction: () -> Unit) {

    val viewModelStoreOwner = LocalViewModelStoreOwner.current
    (viewModelStoreOwner as? NavBackStackEntry)?.let { owner ->
        val viewModel: TopBarViewModel = viewModel(
            viewModelStoreOwner = owner,
            initializer = { TopBarViewModel() },
        )
        LaunchedEffect( sortFilterOnClickAction) {
            viewModel.actions = {
                IconButton(onClick = {sortFilterOnClickAction()}) {
                    Icon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = "Filter",
                        tint = Color.White
                    )
                }
            }
        }
    }
}