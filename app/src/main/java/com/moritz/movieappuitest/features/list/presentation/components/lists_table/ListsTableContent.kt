package com.moritz.movieappuitest.features.list.presentation.components.lists_table

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.moritz.movieappuitest.features.core.presentation.components.swipe.swipeToDeleteContainer
import com.moritz.movieappuitest.features.list.domain.model.MovieList
import com.moritz.movieappuitest.features.list.presentation.components.lists_table.dialogs.AddListDialog
import com.moritz.movieappuitest.features.list.presentation.components.lists_table.dialogs.DeleteListDialog
import com.moritz.movieappuitest.features.list.presentation.components.lists_table.item.ListsTableItem
import com.moritz.movieappuitest.features.list.presentation.screen_list.ListsTableViewModel
import com.moritz.movieappuitest.features.list.presentation.utils.ListsTableContentExtensions.onAddList
import com.moritz.movieappuitest.features.list.presentation.utils.ListsTableContentExtensions.onCancelDeleteList
import com.moritz.movieappuitest.features.list.presentation.utils.ListsTableContentExtensions.onDeleteList

@Composable
fun ListsTableContent(
    myUserMovieLists: List<MovieList>?,
    openAddListDialog: MutableState<Boolean>,
    openDeleteListDialog: MutableState<Boolean>,
    listToDelete: MutableState<MovieList?>,
    navController: NavController,
    listsTableViewModel: ListsTableViewModel,
    context: Context
){

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
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
        onSave = { listName, isPublic -> listsTableViewModel.onAddList(listName, isPublic, openAddListDialog, context)},
        onCancel = {openAddListDialog.value = false}
    )
    DeleteListDialog(
        openDeleteListDialog = openDeleteListDialog.value,
        onDelete = { listsTableViewModel.onDeleteList(listToDelete, openDeleteListDialog) },
        onCancel = { onCancelDeleteList(openDeleteListDialog, navController) }
    )
}