package com.patch4code.loglinemovieapp.features.list.presentation.screen_list

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.list.domain.model.ListSortOptions
import com.patch4code.loglinemovieapp.features.list.presentation.components.list.EmptyListText
import com.patch4code.loglinemovieapp.features.list.presentation.components.list.ListContent
import com.patch4code.loglinemovieapp.features.list.presentation.components.list.dialogs.AddMovieToListDialog
import com.patch4code.loglinemovieapp.features.list.presentation.components.list.dialogs.EditListDialog
import com.patch4code.loglinemovieapp.features.list.presentation.components.list.dialogs.ListSettingsBottomSheet
import com.patch4code.loglinemovieapp.features.list.presentation.components.list.dialogs.ListSortBottomSheet
import com.patch4code.loglinemovieapp.features.list.presentation.components.lists_table.dialogs.DeleteListDialog
import com.patch4code.loglinemovieapp.features.list.presentation.utils.ListDialogsExtensions.onDeleteList
import com.patch4code.loglinemovieapp.features.list.presentation.utils.ListDialogsExtensions.onDeleteListBottomSheet
import com.patch4code.loglinemovieapp.features.list.presentation.utils.ListDialogsExtensions.onEditListBottomSheet
import com.patch4code.loglinemovieapp.features.list.presentation.utils.ListDialogsExtensions.onSaveEditList
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.ProvideTopBarBackNavigationIcon
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.ProvideTopBarSortActionsAndMoreVert
import com.patch4code.loglinemovieapp.features.navigation.presentation.components.ProvideTopBarTitle
import com.patch4code.loglinemovieapp.room_database.LoglineDatabase

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ListsTableView - Composable function representing the list screen view. Displays a certain list
 * of movies and provides options for adding and deleting movies and editing the list name.
 *
 * @author Patch4Code
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListView(
    navController: NavController,
    listId: String?,
    db: LoglineDatabase,
    listViewModel: ListViewModel = viewModel(
        factory = ListViewModelFactory(db.movieListDao, db.movieInListDao)
    )
){
    val movieListId: String = listId ?: return
    val selectedSortOption = remember { mutableStateOf(ListSortOptions.ByPositionAsc) }

    LaunchedEffect(Unit) {
        listViewModel.getList(movieListId, selectedSortOption.value)
    }

    val movieList = listViewModel.movieList.observeAsState().value
    val moviesInList = listViewModel.moviesInList.observeAsState().value

    val openAddMovieDialog = remember { mutableStateOf(false)  }
    val openEditListDialog = remember { mutableStateOf(false)  }
    val openDeleteListDialog = remember { mutableStateOf(false)  }
    val showListSettingsBottomSheet = remember { mutableStateOf(false)  }

    val showSortBottomSheet = remember { mutableStateOf(false)  }

    // TopBar config
    ProvideTopBarTitle(title = Screen.ListScreen.title.asString())
    ProvideTopBarBackNavigationIcon(navController)
    ProvideTopBarSortActionsAndMoreVert(
        sortOnClickAction = { showSortBottomSheet.value = true },
        moreVertOnClickAction = { showListSettingsBottomSheet.value = true }
    )

    Scaffold (
        floatingActionButton = {
            FloatingActionButton(onClick = { openAddMovieDialog.value = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(id = R.string.list_add_movie_description_text))
            }
        }
    ){
        Column {
            Text(text = movieList?.name ?: "N/A", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleLarge)

            if (moviesInList.isNullOrEmpty()){
                EmptyListText()
            }else{
                ListContent(moviesInList, navController, listViewModel, selectedSortOption)
            }
        }
    }
    //Dialogs and BottomSheet
    AddMovieToListDialog(openAddMovieDialog = openAddMovieDialog, sortOption = selectedSortOption.value, listViewModel = listViewModel)

    ListSettingsBottomSheet(showBottomSheet = showListSettingsBottomSheet.value,
        onClose = {showListSettingsBottomSheet.value = false},
        onEdit = { onEditListBottomSheet(showListSettingsBottomSheet, openEditListDialog) },
        onDelete = { onDeleteListBottomSheet(showListSettingsBottomSheet, openDeleteListDialog) })

    EditListDialog(
        initialMovieTitle = movieList?.name ?: "",
        initialIsRankedState = movieList?.isRanked ?: false,
        openEditListDialog = openEditListDialog.value,
        onSave = {newName, isPublic-> listViewModel.onSaveEditList(newName, isPublic, openEditListDialog) },
        onCancel = {openEditListDialog.value = false})

    DeleteListDialog(
        openDeleteListDialog = openDeleteListDialog.value,
        onDelete = { listViewModel.onDeleteList(openDeleteListDialog, navController) },
        onCancel = { openDeleteListDialog.value = false })


    if (movieList != null) {
        ListSortBottomSheet(showSortBottomSheet, selectedSortOption, listViewModel, movieList)
    }
}