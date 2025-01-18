package com.patch4code.loglinemovieapp.features.list.presentation.components.lists_table

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.core.domain.model.SortOption
import com.patch4code.loglinemovieapp.features.core.presentation.components.swipe.SwipeToDeleteContainer
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieList
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieWithListItem
import com.patch4code.loglinemovieapp.features.list.presentation.components.lists_table.dialogs.AddListDialog
import com.patch4code.loglinemovieapp.features.list.presentation.components.lists_table.dialogs.DeleteListDialog
import com.patch4code.loglinemovieapp.features.list.presentation.components.lists_table.dialogs.ListTableSortBottomSheet
import com.patch4code.loglinemovieapp.features.list.presentation.components.lists_table.item.ListsTableItem
import com.patch4code.loglinemovieapp.features.list.presentation.screen_list.ListsTableViewModel
import com.patch4code.loglinemovieapp.features.list.presentation.utils.ListsTableContentExtensions.onAddList
import com.patch4code.loglinemovieapp.features.list.presentation.utils.ListsTableContentExtensions.onDeleteList

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
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
    moviesWithListItems: List<MovieWithListItem>?,
    openAddListDialog: MutableState<Boolean>,
    navController: NavController,
    listsTableViewModel: ListsTableViewModel,
    sortOption: MutableState<SortOption>,
    showSortBottomSheet: MutableState<Boolean>
){

    val openDeleteListDialog = remember { mutableStateOf(false)  }
    val listToDelete = remember { mutableStateOf<MovieList?>(null) }
    val deletingStates = remember { mutableStateMapOf<MovieList, Boolean>() }


    if(myUserMovieLists.isNullOrEmpty()){
        EmptyListTableText()
    }else{
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            itemsIndexed(
                items = myUserMovieLists,
                key = { _, item -> item.hashCode() }
            ) { _, list ->
                val isDeleting = deletingStates[list] ?: false

                SwipeToDeleteContainer(
                    item = list,
                    isDeleting = isDeleting,
                    onDelete = {
                        listToDelete.value = list
                        openDeleteListDialog.value = true
                        deletingStates[list] = true
                    }
                ){_, deleting ->
                    val moviesInSpecificList = moviesWithListItems?.filter { it.movieInList.movieListId == list.id}
                    ListsTableItem(navController, list, moviesInSpecificList, Modifier.alpha(if (deleting) 0f else 1f))
                }
            }
        }
    }
    AddListDialog(
        openAddListDialog = openAddListDialog.value,
        onSave = { listName ->
            listsTableViewModel.onAddList(listName, openAddListDialog, sortOption.value)
        },
        onCancel = {openAddListDialog.value = false}
    )
    DeleteListDialog(
        openDeleteListDialog = openDeleteListDialog.value,
        onDelete = { listsTableViewModel.onDeleteList(listToDelete, openDeleteListDialog, sortOption.value) },
        onCancel = {
            listToDelete.value?.let {
                deletingStates[it] = false
            }
            openDeleteListDialog.value = false
        }
    )
    ListTableSortBottomSheet(showSortBottomSheet , sortOption, listsTableViewModel)
}