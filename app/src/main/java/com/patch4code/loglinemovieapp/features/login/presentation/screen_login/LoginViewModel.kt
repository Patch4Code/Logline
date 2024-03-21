package com.patch4code.loglinemovieapp.features.login.presentation.screen_login

import androidx.lifecycle.ViewModel
import com.parse.ParseException
import com.parse.ParseUser

class LoginViewModel: ViewModel() {


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

    fun login(username: String, password: String, onLoginTriggered:(parseUser: ParseUser?, parseException: ParseException?)->Unit) {
        ParseUser.logInInBackground(username,password) { parseUser: ParseUser?, parseException: ParseException? ->
            onLoginTriggered(parseUser, parseException)
        }
    }




}