package com.patch4code.loglinemovieapp.features.login.presentation.screen_login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parse.ParseException
import com.parse.ParseUser
import com.patch4code.loglinemovieapp.datastore.StoreUserData
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    private lateinit var dataStore: StoreUserData

    fun initializeDataStore(context: Context) {
        dataStore = StoreUserData(context)
    }

    fun signUp(username: String, password: String, passwordAgain: String, onPasswordError:()->Unit, onSignupTriggered:(parseException: ParseException?)->Unit){

        if(password == passwordAgain){
            val user = ParseUser()
            // Set the user's username and password, which can be obtained by a forms
            user.setUsername(username)
            user.setPassword(password)
            user.signUpInBackground { parseException ->
                onSignupTriggered(parseException)
            }
        }else{
            onPasswordError()
        }
    }

    fun login(
        username: String,
        password: String,
        onLoginSuccessful:()->Unit,
        onLoginError:(parseException: ParseException?)->Unit
    ) {
        ParseUser.logInInBackground(username,password) { parseUser: ParseUser?, parseException: ParseException? ->

            if (parseUser != null) {
                viewModelScope.launch {
                    dataStore.saveUserData(parseUser.objectId, parseUser.sessionToken, parseUser.email, parseUser.username)
                }
                onLoginSuccessful()
            }else if (parseException != null){ onLoginError(parseException)}
        }
    }
}