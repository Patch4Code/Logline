package com.patch4code.loglinemovieapp.features.login.presentation.screen_login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginView(onLoginSuccess: () -> Unit, loginViewModel: LoginViewModel = viewModel()){

    val userNameInput = remember { mutableStateOf("") }
    val passwordInput = remember { mutableStateOf("") }

    val showSignupDialog = remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.padding(16.dp))
        
        OutlinedTextField(
            value = userNameInput.value,
            onValueChange = {userNameInput.value = it},
            label = { Text(text = "Username") },
            modifier = Modifier.padding(16.dp)
        )
        OutlinedTextField(
            value = passwordInput.value,
            onValueChange = {passwordInput.value = it},
            label = { Text(text = "Password") },
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = { onLoginSuccess() }) {
            Text(text = "LOGIN")
        }

        Spacer(modifier = Modifier.padding(16.dp))
        
        Text(text = "New here? Sign UP")

        Button(onClick = { showSignupDialog.value = true }) {
            Text(text = "SIGN UP")
        }
    }

    if (showSignupDialog.value) {

        val newUserNameInput = remember { mutableStateOf("") }
        val email = remember { mutableStateOf("") }
        val newPasswordInput = remember { mutableStateOf("") }
        val newPasswordAgainInput = remember { mutableStateOf("") }

        Dialog(onDismissRequest =  {showSignupDialog.value = false}) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    OutlinedTextField(
                        value = newUserNameInput.value,
                        onValueChange = {newUserNameInput.value = it},
                        label = { Text(text = "Username") },
                        modifier = Modifier.padding(16.dp)
                    )
                    OutlinedTextField(
                        value = email.value,
                        onValueChange = {email.value = it},
                        label = { Text(text = "E-mail") },
                        modifier = Modifier.padding(16.dp)
                    )
                    OutlinedTextField(
                        value = newPasswordInput.value,
                        onValueChange = {newPasswordInput.value = it},
                        label = { Text(text = "Password") },
                        modifier = Modifier.padding(16.dp)
                    )
                    OutlinedTextField(
                        value = newPasswordAgainInput.value,
                        onValueChange = {newPasswordAgainInput.value = it},
                        label = { Text(text = "Password again") },
                        modifier = Modifier.padding(16.dp)
                    )
                    Button(onClick = { showSignupDialog.value = false }) {
                        Text(text = "SIGN UP")
                    }
                }
            }
        }
    }
}