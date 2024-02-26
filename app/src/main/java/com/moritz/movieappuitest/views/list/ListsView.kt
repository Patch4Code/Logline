package com.moritz.movieappuitest.views.list

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.moritz.movieappuitest.Screen
import com.moritz.movieappuitest.dataclasses.userMovieListsDummy
import com.moritz.movieappuitest.viewmodels.NavigationViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListsView(navController: NavController, navViewModel: NavigationViewModel){

    LaunchedEffect(Unit) {
        navViewModel.updateScreen(Screen.ListsScreen)
    }

    val openAddListDialog = remember { mutableStateOf(false)  }

    Scaffold (
        floatingActionButton = {
            FloatingActionButton(onClick = { openAddListDialog.value = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add new list")
            }
        },
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                content = {

                    userMovieListsDummy.forEach{ list ->
                        item {
                            ListsItem(navController, list)
                        }
                    }
                }
            )
            AddListDialog(openAddListDialog.value, onSave = {openAddListDialog.value = false}, onCancel = {openAddListDialog.value = false})
        }
    )
}