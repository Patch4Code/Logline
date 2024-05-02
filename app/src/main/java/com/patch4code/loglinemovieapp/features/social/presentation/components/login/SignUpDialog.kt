package com.patch4code.loglinemovieapp.features.social.presentation.components.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.social.presentation.screen_social.LoginViewModel

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * SignUpDialog - Composable function for displaying the signup dialog.
 *
 * @author Patch4Code
 */
@Composable
fun SignUpDialog(showSignupDialog: MutableState<Boolean>, loginViewModel: LoginViewModel){

    if (showSignupDialog.value) {

        val context = LocalContext.current

        // Strings for toast messages
        val passwordErrorToastText = stringResource(id = R.string.password_must_be_same_toast)
        val signupSuccessToastText = stringResource(id = R.string.signup_success_toast)

        val newUserNameInput = remember { mutableStateOf("") }
        val email = remember { mutableStateOf("") }
        val newPasswordInput = remember { mutableStateOf("") }
        val newPasswordAgainInput = remember { mutableStateOf("") }

        Dialog(onDismissRequest =  {showSignupDialog.value = false}) {
            Surface(shape = RoundedCornerShape(16.dp),) {
                Column(modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    // Title
                    Text(text = stringResource(id = R.string.signup_dialog_title),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp,bottom = 8.dp)
                    )
                    // Username input field
                    LoginOutlinedTextField(input = newUserNameInput, label = stringResource(id = R.string.username_label))
                    Spacer(modifier = Modifier.padding(4.dp))
                    // Email input field
                    LoginOutlinedTextField(input = email, label = stringResource(id = R.string.email_label))
                    Spacer(modifier = Modifier.padding(8.dp))
                    // Password input fields
                    PasswordOutlinedTextField(passwordInput = newPasswordInput)
                    Spacer(modifier = Modifier.padding(4.dp))
                    PasswordOutlinedTextField(passwordInput = newPasswordAgainInput, label = stringResource(id = R.string.password_again_label))

                    Spacer(modifier = Modifier.padding(16.dp))

                    // Sign-up button
                    OutlinedButton(onClick = {
                        loginViewModel.signUp(
                            username = newUserNameInput.value,
                            email = email.value,
                            password = newPasswordInput.value,
                            passwordAgain = newPasswordAgainInput.value,
                            onPasswordError = {
                                Toast.makeText(context, passwordErrorToastText, Toast.LENGTH_SHORT).show()
                            },
                            onSignupTriggered = {parseException->
                                if(parseException == null){
                                    Toast.makeText(context, signupSuccessToastText, Toast.LENGTH_LONG).show()
                                    showSignupDialog.value = false
                                }else{
                                    Toast.makeText(context, parseException.message, Toast.LENGTH_LONG).show()
                                    Log.e("SocialViewModel", "parseException: ${parseException.message}")
                                }
                            }
                        )
                    }) {
                        Text(text = stringResource(id = R.string.signup_button_text))
                    }

                    Spacer(modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}