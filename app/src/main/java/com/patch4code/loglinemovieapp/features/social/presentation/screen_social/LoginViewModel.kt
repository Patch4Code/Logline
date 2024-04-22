package com.patch4code.loglinemovieapp.features.social.presentation.screen_social

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parse.ParseACL
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.ParseUser
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.presentation.utils.JSONHelper.toJson
import com.patch4code.loglinemovieapp.preferences_datastore.StoreUserData
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

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
            // Set the user's username and password
            user.setUsername(username)
            user.setEmail(email)
            user.setPassword(password)

            // Define userProfile
            val userProfile = ParseObject("UserProfile")
            userProfile.put("userName", username)
            val favouriteMoviesString = listOf(Movie(), Movie(), Movie(), Movie()).toJson()
            userProfile.put("favouriteMoviesString", favouriteMoviesString)

            user.signUpInBackground { parseException ->
                if (parseException == null) {
                    // When user was created successfully, also set userProfiles user to current user
                    userProfile.put("user", ParseUser.getCurrentUser())
                    val acl = ParseACL()
                    acl.setWriteAccess(ParseUser.getCurrentUser(), true)
                    acl.setReadAccess(ParseUser.getCurrentUser(), true)
                    userProfile.acl = acl

                    userProfile.saveInBackground { profileException ->
                        if (profileException == null) {
                            // Set UserProfile-Object for the user
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
                    // Error when retrieving the UserProfile object
                    onLoginError(fetchException)
                }
            }

            // save user data in shared preferences
            if (parseUser != null) {
                viewModelScope.launch {
                    dataStore.saveUserData(parseUser.objectId, parseUser.sessionToken, parseUser.email, parseUser.username)
                }
                onLoginSuccessful()
            }else if (parseException != null){ onLoginError(parseException)}
        }
    }
}