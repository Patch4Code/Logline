package com.patch4code.loglinemovieapp.features.social.presentation.components.social

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.patch4code.loglinemovieapp.features.social.presentation.components.login.LoginOutlinedTextField
import com.patch4code.loglinemovieapp.features.social.presentation.components.login.LoginTooltip
import com.patch4code.loglinemovieapp.features.social.presentation.components.login.PasswordOutlinedTextField
import com.patch4code.loglinemovieapp.features.social.presentation.components.login.SignUpDialog
import com.patch4code.loglinemovieapp.features.social.presentation.screen_social.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SocialLoginCard(loginViewModel: LoginViewModel = viewModel()){

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        loginViewModel.initializeDataStore(context)
    }

    val userNameInput = remember { mutableStateOf("") }
    val passwordInput = remember { mutableStateOf("") }

    val showSignupDialog = remember { mutableStateOf(false) }

    Card{
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
        ) {

            Row (modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp,bottom = 8.dp),
                horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically
            ){
                Text(text = "Login to your Logline Account", style = MaterialTheme.typography.titleMedium)
                LoginTooltip()
            }


            LoginOutlinedTextField(input = userNameInput, label = "Username")
            Spacer(modifier = Modifier.padding(4.dp))
            PasswordOutlinedTextField(passwordInput = passwordInput)
            Spacer(modifier = Modifier.padding(8.dp))

            Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                Button(
                    onClick = {
                        loginViewModel.login(
                            username = userNameInput.value,
                            password = passwordInput.value,
                            onLoginSuccessful = { Toast.makeText(context, "Successful Login", Toast.LENGTH_LONG).show() },
                            onLoginError = { Toast.makeText(context, "Login Error: ${it!!.message}", Toast.LENGTH_LONG).show()}
                        ) },
                    content = { Text(text = "LOGIN")  }
                )
                Spacer(modifier = Modifier.padding(8.dp))
                OutlinedButton(onClick = { showSignupDialog.value = true }) {
                    Text(text = "SIGN UP")
                }
            }
        }
    }
    SignUpDialog(showSignupDialog = showSignupDialog, loginViewModel = loginViewModel)
}