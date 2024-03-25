package com.patch4code.loglinemovieapp.features.social.presentation.screen_social

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parse.ParseException
import com.parse.ParseUser
import com.patch4code.loglinemovieapp.datastore.StoreUserData
import kotlinx.coroutines.launch

class SocialViewModel: ViewModel() {

    private lateinit var dataStore: StoreUserData

    fun initializeDataStore(context: Context) {
        dataStore = StoreUserData(context)
    }


    fun signUp(
        username: String,
        email: String,
        password: String,
        passwordAgain: String,
        onPasswordError:()->Unit,
        onSignupTriggered:(parseException: ParseException?)->Unit
    ) {
        if(password == passwordAgain){
            val user = ParseUser()
            // Set the user's username and password, which can be obtained by a forms
            user.setUsername(username)
            user.setEmail(email)
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


    fun logout(onLogoutSuccessful:()->Unit, onLogoutError:(parseException: ParseException?)->Unit){
        ParseUser.logOutInBackground { e: ParseException? ->
            if (e == null){
                onLogoutSuccessful()

                //clear user-datastore
                viewModelScope.launch {
                    dataStore.deleteUserData()
                }

            }else{
                onLogoutError(e)
            }
        }
    }

    fun changeProfileVisibilityState(publicState : Boolean){
        viewModelScope.launch {
            dataStore.setProfilePublicState(publicState)
        }
        //here the actions with back4app
    }
    fun updatePublicProfile(){
        //here the actions with back4app
    }
}