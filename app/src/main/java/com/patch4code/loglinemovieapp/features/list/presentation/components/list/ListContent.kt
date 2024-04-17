package com.patch4code.loglinemovieapp.features.list.presentation.components.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.presentation.components.swipe.swipeToDeleteContainer
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieList
import com.patch4code.loglinemovieapp.features.list.presentation.components.list.dialogs.MakeListPublicDialog
import com.patch4code.loglinemovieapp.features.list.presentation.components.list.items.ListItem
import com.patch4code.loglinemovieapp.features.list.presentation.screen_list.ListViewModel
import com.patch4code.loglinemovieapp.preferences_datastore.StoreUserData

@Composable
fun ListContent(
    movieList: MovieList?,
    showBottomSheet: MutableState<Boolean>,
    openDeleteMovieDialog : MutableState<Boolean>,
    movieToDelete:  MutableState<Movie?>,
    navController: NavController,
    listViewModel: ListViewModel
){

    val context = LocalContext.current
    val dataLoginStore = remember { StoreUserData(context) }
    val savedLoginData = dataLoginStore.getUserId.collectAsState(initial = "")
    val openMakeListPublicDialog = remember { mutableStateOf(false)  }

    Column {
        Row (
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = movieList?.name ?: "N/A", modifier = Modifier.weight(1f), style = MaterialTheme.typography.titleLarge)
            IconButton(enabled = savedLoginData.value?.isNotEmpty() == true,
                onClick = { openMakeListPublicDialog.value = true}) {
                Icon(imageVector = Icons.Default.Public, contentDescription = "Make Public")
            }
            IconButton(onClick = { showBottomSheet.value = true }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Open List Settings")
            }
        }

        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
        ){
            itemsIndexed(
                items = movieList?.movies ?: emptyList(),
                key = { _, item -> item.hashCode() }
            ) { _, movie ->
                swipeToDeleteContainer(
                    item = movie,
                    onDelete = {
                        movieToDelete.value = movie
                        openDeleteMovieDialog.value = true
                    }
                ) {
                    ListItem(navController, movie)
                }
            }
        }
    }
    MakeListPublicDialog(openMakeListPublicDialog, listViewModel)
}