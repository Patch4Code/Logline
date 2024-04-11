package com.patch4code.loglinemovieapp.features.social.presentation.screen_social

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.ParseUser
import com.patch4code.loglinemovieapp.preferences_datastore.StoreUserData
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

            val userProfile = ParseObject("UserProfile")
            userProfile.put("userName", username)

            user.signUpInBackground { parseException ->
                if (parseException == null) {
                    // Benutzer erfolgreich erstellt, speichere auch das UserProfile-Objekt
                    userProfile.put("user", ParseUser.getCurrentUser()) // Weise dem UserProfile den aktuellen Benutzer zu
                    userProfile.saveInBackground { profileException ->
                        if (profileException == null) {
                            // Setze das UserProfile-Objekt im Benutzerobjekt
                            user.put("userProfile", userProfile)
                            user.saveInBackground { userException ->
                                onSignupTriggered(userException)
                            }
                        } else {
                            onSignupTriggered(profileException)
                        }
                    }
                } else {
                    onSignupTriggered(parseException)
                }
            }
        } else {
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

            // check if profile is public and save the state
            val userDataPointer = parseUser?.getParseObject("userProfile") // Hole den Pointer auf das UserProfile-Objekt
            userDataPointer?.fetchInBackground { userProfile: ParseObject?, fetchException: ParseException? ->
                if (userProfile != null) {
                    val isPublic = userProfile.getBoolean("isPublic")
                    viewModelScope.launch {
                        dataStore.setProfilePublicState(isPublic)
                    }
                } else if (fetchException != null) {
                    // Fehler beim Abrufen des UserProfile-Objekts
                    onLoginError(fetchException)
                }
            }

            // save user data
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

    fun changeProfileVisibilityState(publicState : Boolean, onSuccess: () -> Unit, onError: (parseException: ParseException?) -> Unit){
        val user = ParseUser.getCurrentUser()
        val userDataPointer = user.getParseObject("userProfile")

        Log.e("SocialViewModel", "changeProfileVisibilityState - userDataPointer: $userDataPointer")

        userDataPointer?.fetchInBackground { userProfile: ParseObject?, fetchException: ParseException? ->
            if (userProfile != null) {
                // Update des isPublic-Feldes im UserProfile-Objekt
                userProfile.put("isPublic", publicState)
                userProfile.saveInBackground { saveException ->
                    if (saveException == null) {
                        viewModelScope.launch {
                            dataStore.setProfilePublicState(publicState)
                        }
                        Log.e("SocialViewModel", "onSuccess - changed isPublic to: $publicState ")
                        onSuccess()
                    } else {
                        onError(saveException)
                        Log.e("SocialViewModel", "error saveException: ${saveException.message}")
                    }
                }
            } else if (fetchException != null) {
                Log.e("SocialViewModel", "error fetchException: ${fetchException.message}")
                onError(fetchException)
            }
            else{
                Log.e("SocialViewModel", "error and nothing happened")
            }
        }
    }


    fun updatePublicProfile(){
        //here the actions with back4app
    }
}