package com.moritz.movieappuitest.views.list.details

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Public
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.moritz.movieappuitest.Screen
import com.moritz.movieappuitest.dataclasses.Movie
import com.moritz.movieappuitest.dataclasses.MovieList
import com.moritz.movieappuitest.utils.JSONHelper
import com.moritz.movieappuitest.viewmodels.ListViewModel
import com.moritz.movieappuitest.viewmodels.NavigationViewModel
import com.moritz.movieappuitest.views.swipe.swipeToDeleteContainer
import java.net.URLDecoder

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListView(navController: NavController, navViewModel: NavigationViewModel, movieListString: String?, listViewModel: ListViewModel = viewModel()){

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.ListScreen)
    }
    val decodedMovieListString = URLDecoder.decode(movieListString, "UTF-8")
    val movieListData: MovieList = JSONHelper.fromJson(decodedMovieListString)

    listViewModel.setList(movieListData)
    val movieList = listViewModel.movieList.observeAsState().value

    val openAddMovieDialog = remember { mutableStateOf(false)  }
    val openDeleteMovieDialog = remember { mutableStateOf(false)  }
    val movieToDelete = remember { mutableStateOf<Movie?>(null) }


    Scaffold (
        floatingActionButton = {
            FloatingActionButton(onClick = {  }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add new movie to list")
            }
        }
    ){
        Column {
            Row (modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically){
                Text(text = movieList?.name ?: "N/A", modifier = Modifier.weight(1f), style = MaterialTheme.typography.titleLarge)
                Icon(
                    imageVector = if(movieList?.isPublic == true) Icons.Default.Public else Icons.Default.Lock,
                    contentDescription = if(movieList?.isPublic == true) "Public List" else "Private List",
                )
            }

            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)){
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
        DeleteMovieFromListDialog(openDeleteMovieDialog = openDeleteMovieDialog.value,
            onDelete = {
                //delete movie from list
                movieToDelete.value?.id?.let { movieToDelete->
                    listViewModel.removeMovieFromList(movieToDelete)
                }
                movieToDelete.value = null
                openDeleteMovieDialog.value = false
            },
            onCancel = {

                openDeleteMovieDialog.value = false

                //workaround with scene reload

            }
        )
    }
}