package com.patch4code.loglinemovieapp.features.social.presentation.screen_social

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.parse.ParseException
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseUser
import com.patch4code.loglinemovieapp.preferences_datastore.StoreUserData
import com.patch4code.loglinemovieapp.room_database.UserProfileDao
import kotlinx.coroutines.launch
import java.io.File

class SocialViewModel(private val dao: UserProfileDao): ViewModel() {

    private lateinit var dataStore: StoreUserData

    fun initializeDataStore(context: Context) {
        dataStore = StoreUserData(context)
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

    fun changeProfileVisibilityState(publicState : Boolean, onSuccess: () -> Unit, onError: (exception: Exception?) -> Unit){
        viewModelScope.launch {
            try {
                val user = ParseUser.getCurrentUser()
                val userDataPointer = user.getParseObject("userProfile")

                userDataPointer?.fetchInBackground { userProfile: ParseObject?, fetchException: ParseException? ->
                    if (userProfile != null) {
                        // Update of the isPublic field in the UserProfile object
                        userProfile.put("isPublic", publicState)
                        userProfile.saveInBackground { saveException ->
                            if (saveException == null) {
                                viewModelScope.launch {
                                    dataStore.setProfilePublicState(publicState)
                                }
                                //Log.e("SocialViewModel", "onSuccess - changed isPublic to: $publicState ")
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
                }
            }catch (e: Exception){
                onError(e)
                Log.e("SocialViewModel", "Error changing ProfileVisibilityState: ", e)
            }
        }
    }


    fun updatePublicProfile(){
        viewModelScope.launch {
            val localUserProfile = dao.getUserProfile()
            try {
                val user = ParseUser.getCurrentUser()
                val userDataPointer = user.getParseObject("userProfile")

                userDataPointer?.fetchInBackground { userProfile: ParseObject?, fetchException: ParseException? ->
                    if (userProfile != null) {
                        // upload and save profile image
                        localUserProfile?.profileImagePath?.let { imagePath ->
                            if(!imagePath.isNullOrEmpty()){
                                Log.e("SocialViewModel", "profile image Path: $imagePath")

                                //dateipfad falsch!!!
                                // give: file:///data/user/0/com.patch4code.loglinemovieapp/files/profile_image.jpg
                                //needed: /data/data/com.patch4code.loglinemovieapp/files/profile_image.jpg

                                val profileImageFile = ParseFile(File("/data/data/com.patch4code.loglinemovieapp/files/profile_image.jpg").readBytes(), "profile_image.jpg")
                                profileImageFile.saveInBackground({ profileImageSaveException ->
                                    if (profileImageSaveException == null) {
                                        userProfile.put("profileImage", profileImageFile)
                                        // Profilbild wurde hochgeladen und im Benutzerprofil gespeichert
                                    } else {
                                        // Fehler beim Hochladen des Profilbilds
                                        Log.e("SocialViewModel", "Error uploading profile image", profileImageSaveException)
                                    }
                                }, { progress ->
                                    // Hier kannst du den Fortschritt des Hochladens überwachen
                                })
                            }
                        }


                        //userProfile.put("pofileImage", )
                        //userProfile.put("bannerImage", )
                        //userProfile.put("bioText", localUserProfile?.bioText ?: "")
                        //userProfile.put("favouriteMovies", )

                        userProfile.saveInBackground { saveException ->
                            if (saveException == null) {
                                Log.e("SocialViewModel", "Update profile successful")
                            } else {
                                Log.e("SocialViewModel", "Error updating profile")
                            }
                        }


                    }else{
                        //Error user profile not found
                    }
                }
            }catch (e: Exception){
                Log.e("SocialViewModel", "Error updating Profile: ", e)
            }
        }
    }
}


class SocialViewModelFactory(private val dao: UserProfileDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SocialViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SocialViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}