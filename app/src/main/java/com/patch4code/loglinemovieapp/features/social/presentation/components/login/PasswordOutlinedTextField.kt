package com.patch4code.loglinemovieapp.features.social.presentation.components.login

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun PasswordOutlinedTextField(passwordInput: MutableState<String>, label: String = "Password"){

    val showPassword = remember { mutableStateOf(false) }

    OutlinedTextField(
        value = passwordInput.value,
        onValueChange = {passwordInput.value = it},
        label = { Text(text = label) },
        modifier = Modifier.padding(16.dp),
        singleLine = true,
        visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { showPassword.value = !showPassword.value }) {
                Icon(
                    imageVector =
                    if (showPassword.value) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                    contentDescription = null
                )
            }
        }
    )
}