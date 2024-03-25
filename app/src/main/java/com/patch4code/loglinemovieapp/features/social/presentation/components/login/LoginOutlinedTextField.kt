package com.patch4code.loglinemovieapp.features.social.presentation.components.login

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginOutlinedTextField(input: MutableState<String>, label: String){
    OutlinedTextField(
        value = input.value,
        onValueChange = {input.value = it},
        label = { Text(text = label) },
        modifier = Modifier.padding(16.dp),
        singleLine = true,
    )
}