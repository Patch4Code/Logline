package com.patch4code.loglinemovieapp.features.list.presentation.components.lists_table

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.core.presentation.components.swipe.swipeToDeleteContainer
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieList
import com.patch4code.loglinemovieapp.features.list.presentation.components.lists_table.dialogs.AddListDialog
import com.patch4code.loglinemovieapp.features.list.presentation.components.lists_table.dialogs.DeleteListDialog
import com.patch4code.loglinemovieapp.features.list.presentation.components.lists_table.item.ListsTableItem
import com.patch4code.loglinemovieapp.features.list.presentation.screen_list.ListsTableViewModel
import com.patch4code.loglinemovieapp.features.list.presentation.utils.ListsTableContentExtensions.onAddList
import com.patch4code.loglinemovieapp.features.list.presentation.utils.ListsTableContentExtensions.onCancelDeleteList
import com.patch4code.loglinemovieapp.features.list.presentation.utils.ListsTableContentExtensions.onDeleteList

/**
 * APACHE LICENSE, VERSION 2.0 (https://www.apache.org/licenses/LICENSE-2.0)
 *
 * ListsTableContent - Composable function displaying the content of the lists table.
 * Includes a LazyColumn of the list items and dialog components for adding and deleting lists
 * as well as providing swipe to delete functionality.
 *
 * @author Patch4Code
 */
@Composable
fun ListsTableContent(
    myUserMovieLists: List<MovieList>?,
    openAddListDialog: MutableState<Boolean>,
    openDeleteListDialog: MutableState<Boolean>,
    listToDelete: MutableState<MovieList?>,
    navController: NavController,
    listsTableViewModel: ListsTableViewModel
){

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        itemsIndexed(
            items = myUserMovieLists ?: emptyList(),
            key = { _, item -> item.hashCode() }
        ) { _, list ->
            swipeToDeleteContainer(
                item = list,
                onDelete = {
                    listToDelete.value = list
                    openDeleteListDialog.value = true
                }
            ) {
                ListsTableItem(navController, list)
            }
        }
    }
    AddListDialog(
        openAddListDialog = openAddListDialog.value,
        onSave = { listName, isRanked ->
            listsTableViewModel.onAddList(listName, isRanked, openAddListDialog)
        },
        onCancel = {openAddListDialog.value = false}
    )
    DeleteListDialog(
        openDeleteListDialog = openDeleteListDialog.value,
        onDelete = { listsTableViewModel.onDeleteList(listToDelete, openDeleteListDialog) },
        onCancel = { onCancelDeleteList(openDeleteListDialog, navController) }
    )
}