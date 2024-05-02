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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.social.presentation.components.login.LoginOutlinedTextField
import com.patch4code.loglinemovieapp.features.social.presentation.components.login.LoginTooltip
import com.patch4code.loglinemovieapp.features.social.presentation.components.login.PasswordOutlinedTextField
import com.patch4code.loglinemovieapp.features.social.presentation.components.login.SignUpDialog
import com.patch4code.loglinemovieapp.features.social.presentation.screen_social.LoginViewModel

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * SocialLoginCard - Composable function that displays the login-card.
 *
 * @author Patch4Code
 */
@Composable
fun SocialLoginCard(loginViewModel: LoginViewModel = viewModel()){

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        loginViewModel.initializeDataStore(context)
    }

    val userNameInput = remember { mutableStateOf("") }
    val passwordInput = remember { mutableStateOf("") }

    val showSignupDialog = remember { mutableStateOf(false) }

    // Toast messages
    val successToastText = stringResource(id = R.string.login_success_toast)
    val errorToastText = stringResource(id = R.string.login_error_toast)

    Card{
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
        ) {
            // Title row with login tooltip
            Row (modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp,bottom = 8.dp),
                horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically
            ){
                Text(text = stringResource(id = R.string.login_card_title), style = MaterialTheme.typography.titleMedium)
                LoginTooltip()
            }

            // Username and password fields
            LoginOutlinedTextField(input = userNameInput, label = stringResource(id = R.string.username_label))
            Spacer(modifier = Modifier.padding(4.dp))
            PasswordOutlinedTextField(passwordInput = passwordInput)
            Spacer(modifier = Modifier.padding(8.dp))

            // Login and signup buttons
            Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                Button(
                    onClick = {
                        loginViewModel.login(
                            username = userNameInput.value,
                            password = passwordInput.value,
                            onLoginSuccessful = { Toast.makeText(context, successToastText, Toast.LENGTH_LONG).show() },
                            onLoginError = { Toast.makeText(context, "$errorToastText ${it!!.message}", Toast.LENGTH_LONG).show()}
                        ) },
                    content = { Text(text = stringResource(id = R.string.login_button_text))  }
                )
                Spacer(modifier = Modifier.padding(8.dp))
                OutlinedButton(onClick = { showSignupDialog.value = true }) {
                    Text(text = stringResource(id = R.string.signup_button_text))
                }
            }
        }
    }
    SignUpDialog(showSignupDialog = showSignupDialog, loginViewModel = loginViewModel)
}