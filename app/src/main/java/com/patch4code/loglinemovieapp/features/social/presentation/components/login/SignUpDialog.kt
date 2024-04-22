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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.patch4code.loglinemovieapp.features.social.presentation.screen_social.LoginViewModel

@Composable
fun SignUpDialog(showSignupDialog: MutableState<Boolean>, loginViewModel: LoginViewModel){

    if (showSignupDialog.value) {

        val context = LocalContext.current

        val newUserNameInput = remember { mutableStateOf("") }
        val email = remember { mutableStateOf("") }
        val newPasswordInput = remember { mutableStateOf("") }
        val newPasswordAgainInput = remember { mutableStateOf("") }

        Dialog(onDismissRequest =  {showSignupDialog.value = false}) {
            Surface(
                //modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(text = "Signup",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp,bottom = 8.dp)
                    )

                    LoginOutlinedTextField(input = newUserNameInput, label = "Username")
                    Spacer(modifier = Modifier.padding(4.dp))
                    LoginOutlinedTextField(input = email, label = "E-mail")

                    Spacer(modifier = Modifier.padding(8.dp))

                    PasswordOutlinedTextField(passwordInput = newPasswordInput)
                    Spacer(modifier = Modifier.padding(4.dp))
                    PasswordOutlinedTextField(passwordInput = newPasswordAgainInput, label = "Password again")


                    Spacer(modifier = Modifier.padding(16.dp))

                    OutlinedButton(onClick = {
                        loginViewModel.signUp(
                            username = newUserNameInput.value,
                            email = email.value,
                            password = newPasswordInput.value,
                            passwordAgain = newPasswordAgainInput.value,
                            onPasswordError = {
                                Toast.makeText(context, "Password must be the same", Toast.LENGTH_SHORT).show()
                            },
                            onSignupTriggered = {parseException->
                                if(parseException == null){
                                    Toast.makeText(context, "Signup Successfully! You can now login.", Toast.LENGTH_LONG).show()
                                    showSignupDialog.value = false
                                }else{
                                    Toast.makeText(context, parseException.message, Toast.LENGTH_LONG).show()
                                    Log.e("SocialViewModel", "parseException: ${parseException.message}")
                                }
                            }
                        )
                    }) {
                        Text(text = "SIGN UP")
                    }

                    Spacer(modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}