package com.moritz.movieappuitest.userinterface.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(navController: NavController){

    val textInput = remember { mutableStateOf("")}

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.Center
    )
    {
        OutlinedTextField(
            value = textInput.value,
            onValueChange = {textInput.value = it},
            label = { Text(text = "Search Movie/Series")}
        )

        IconButton(
            onClick = {},
            modifier = Modifier.align(Alignment.CenterVertically)) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
        }
    }
}