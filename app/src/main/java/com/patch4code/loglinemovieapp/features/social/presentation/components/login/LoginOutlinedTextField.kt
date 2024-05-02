package com.patch4code.loglinemovieapp.features.social.presentation.components.login

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * LoginOutlinedTextField - Composable function for displaying an outlined text-field for login.
 *
 * @author Patch4Code
 */
@Composable
fun LoginOutlinedTextField(input: MutableState<String>, label: String){
    OutlinedTextField(
        value = input.value,
        onValueChange = {input.value = it},
        label = { Text(text = label) },
        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            autoCorrect = false
        )
    )
}