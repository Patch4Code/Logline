package com.patch4code.loglinemovieapp.features.core.presentation.components.filter_dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FilterApplyButton(onClick:() -> Unit){
    Button(
        modifier = Modifier.fillMaxWidth().padding(32.dp),
        onClick = { onClick() },
        content = { Text("Apply Filters") }
    )
}