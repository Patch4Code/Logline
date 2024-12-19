package com.patch4code.loglinemovieapp.features.core.presentation.components.filter_dialog

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterTopBarSection(onCloseDialog:() -> Unit, onResetClick:() -> Unit){

    TopAppBar(
        title = {
            Text(
                text = "Sort and Filter",
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            IconButton(onClick = { onCloseDialog() }) {
                Icon(Icons.Default.Close, contentDescription = "Close Dialog")
            }
        },
        actions = {
            TextButton(
                onClick = { onResetClick() },
                content = { Text("reset") }
            )
        }
    )
}