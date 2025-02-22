package com.patch4code.logline.features.navigation.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.patch4code.logline.R
import com.patch4code.logline.features.navigation.presentation.screen_navigation.TopBarViewModel

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * TopBar -Composable function to display the top bar.
 * Displays the title of the  current screen (from navViewModel) and a navigation Icon
 * as well as an optional action.
 *
 * @author Patch4Code
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior?,
    onDrawerStateChanged: () -> Unit
){

    val backStackEntry by navController.currentBackStackEntryAsState()

    backStackEntry?.let { entry ->

        val navViewModel: TopBarViewModel = viewModel(
            viewModelStoreOwner = entry,
            initializer = { TopBarViewModel() },
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