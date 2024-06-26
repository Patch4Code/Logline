package com.patch4code.loglinemovieapp.features.list.presentation.screen_list

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieList
import com.patch4code.loglinemovieapp.features.list.presentation.components.lists_table.ListsTableContent
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel
import com.patch4code.loglinemovieapp.room_database.LoglineDatabase

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ListsTableView - Composable function representing the lists-table screen view.
 * Displays the user's movie lists and provides options to add, delete and navigate to a list.
 *
 * @author Patch4Code
 */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListsTableView(
    navController: NavController,
    navViewModel: NavigationViewModel,
    db: LoglineDatabase,
    listsTableViewModel: ListsTableViewModel = viewModel(
        factory = ListsTableViewModelFactory(db.movieListDao)
    )
){

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.ListsTableScreen)
        listsTableViewModel.updateUserMovieLists()
    }

    val openAddListDialog = remember { mutableStateOf(false)  }
    val openDeleteListDialog = remember { mutableStateOf(false)  }
    val listToDelete = remember { mutableStateOf<MovieList?>(null) }

    val myUserMovieLists = listsTableViewModel.userMovieLists.observeAsState().value

    Scaffold (
        floatingActionButton = {
            // ActionButton to add a new list
            FloatingActionButton(onClick = { openAddListDialog.value = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(id = R.string.list_table_add_list_description_text))
            }
        },
        content = {
            ListsTableContent(
                myUserMovieLists = myUserMovieLists,
                openAddListDialog = openAddListDialog,
                openDeleteListDialog = openDeleteListDialog,
                listToDelete = listToDelete,
                navController = navController,
                listsTableViewModel = listsTableViewModel,
            )
        }
    )
}