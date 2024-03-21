package com.patch4code.loglinemovieapp.features.login.presentation.screen_login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.patch4code.loglinemovieapp.features.login.presentation.components.LoginOutlinedTextField
import com.patch4code.loglinemovieapp.features.login.presentation.components.PasswordOutlinedTextField
import com.patch4code.loglinemovieapp.features.login.presentation.components.SignUpDialog

@Composable
fun LoginView(onLoginSuccess: () -> Unit, loginViewModel: LoginViewModel = viewModel()){

    val context = LocalContext.current

    val userNameInput = remember { mutableStateOf("") }
    val passwordInput = remember { mutableStateOf("") }

    val showSignupDialog = remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.padding(16.dp))
        LoginOutlinedTextField(input = userNameInput, label = "Username")
        PasswordOutlinedTextField(passwordInput = passwordInput)
        Button(onClick = {
            loginViewModel.login(
                username = userNameInput.value,
                password = passwordInput.value,
                onLoginTriggered = { parseUser, parseException->
                    if(parseUser != null){
                        Toast.makeText(context, "Successful Login", Toast.LENGTH_LONG).show()
                        Log.e("LoginView","${parseUser.email}, ${parseUser}")
                        onLoginSuccess()
                    } else{
                        if(parseException != null){
                            Toast.makeText(context, "Login Error: ${parseException.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            )
        }) {
            Text(text = "LOGIN")
        }
        Spacer(modifier = Modifier.padding(16.dp))
        
        Text(text = "New here? Sign UP")

        Button(onClick = { showSignupDialog.value = true }) {
            Text(text = "SIGN UP")
        }
    }

    SignUpDialog(showSignupDialog = showSignupDialog, loginViewModel = loginViewModel)
}