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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieList
import com.patch4code.loglinemovieapp.features.list.presentation.components.lists_table.ListsTableContent
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.NavigationViewModel
import com.patch4code.loglinemovieapp.room_database.LoglineDatabase

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
            FloatingActionButton(onClick = { openAddListDialog.value = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add new list")
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