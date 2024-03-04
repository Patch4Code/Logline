package com.moritz.movieappuitest.views.list.overview.tablecontent

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
import com.moritz.movieappuitest.dataclasses.MovieList
import com.moritz.movieappuitest.viewmodels.ListsTableViewModel
import com.moritz.movieappuitest.views.list.overview.dialogs.AddListDialog
import com.moritz.movieappuitest.views.list.overview.dialogs.DeleteListDialog
import com.moritz.movieappuitest.views.list.overview.item.ListsTableItem
import com.moritz.movieappuitest.views.swipe.swipeToDeleteContainer

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