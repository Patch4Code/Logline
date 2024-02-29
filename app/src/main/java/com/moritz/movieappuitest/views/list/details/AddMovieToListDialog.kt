package com.moritz.movieappuitest.views.list.details

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMovieToListDialog(openAddMovieDialog: Boolean, onAdd:()->Unit, onCancel:()->Unit){

    if(openAddMovieDialog){

        val textInput = remember { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current

        //val searchResult = searchViewModel.searchedMovies.observeAsState().value


        AlertDialog(
            onDismissRequest = { onCancel() },//openDiscardDialog.value = false
            title = { Text(text = "Search Movie to add to list") },
            text = {
                OutlinedTextField(
                    value = textInput.value,
                    onValueChange = {textInput.value = it},
                    label = { Text(text = "Search Movie")},
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            if(textInput.value.isNotBlank()){
                                keyboardController?.hide()

                                //searchViewModel.searchMovie(textInput.value)
                            }
                        }
                    ),
                    trailingIcon = {
                        IconButton(onClick = {
                            if(textInput.value.isNotBlank()){
                                keyboardController?.hide()

                                //searchViewModel.searchMovie(textInput.value)
                            }
                        }) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")}
                        }
                )
                LazyColumn(modifier = Modifier.padding(top =8.dp)){

                }


            },
            confirmButton = {
                Button(onClick = { onAdd() }) {
                    Text(text = "Add Movie")
                }
            },
            dismissButton = {
                Button(onClick = { onCancel() }) {
                    Text(text = "Cancel")
                }
            }
        )

    }
}