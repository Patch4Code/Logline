package com.patch4code.loglinemovieapp.features.social.presentation.screen_social

import android.content.Context
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.parse.ParseACL
import com.parse.ParseException
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseUser
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.presentation.utils.JSONHelper.toJson
import com.patch4code.loglinemovieapp.preferences_datastore.StoreUserData
import com.patch4code.loglinemovieapp.room_database.UserProfileDao
import kotlinx.coroutines.launch

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * SocialViewModel - ViewModel responsible for managing social data and actions.
 *
 * @param dao The UserProfileDao instance for accessing user profile data from the db
 * @author Patch4Code
 */
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

                val acl = ParseACL()
                acl.setWriteAccess(ParseUser.getCurrentUser(), true)
                acl.setReadAccess(ParseUser.getCurrentUser(), true)
                acl.publicReadAccess = publicState

                userDataPointer?.fetchInBackground { userProfile: ParseObject?, fetchException: ParseException? ->
                    if (userProfile != null) {
                        // Update of the isPublic field in the UserProfile object
                        userProfile.put("isPublic", publicState)
                        userProfile.acl = acl

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


    fun updatePublicProfile(context: Context, onSuccess:()->Unit, onError:(error: String)->Unit){
        viewModelScope.launch {
            val localUserProfile = dao.getUserProfile()

            // // Initialize variables and saves profile and banner image bytes when images are available
            var profileImageBytes: ByteArray? = null
            if(!localUserProfile?.profileImagePath.isNullOrEmpty()){
                val inputStream = localUserProfile?.profileImagePath?.let { context.contentResolver.openInputStream(it.toUri()) }
                profileImageBytes = inputStream?.readBytes()
            }
            var bannerImageBytes: ByteArray? = null
            if(!localUserProfile?.bannerImagePath.isNullOrEmpty()){
                val inputStream = localUserProfile?.bannerImagePath?.let { context.contentResolver.openInputStream(it.toUri()) }
                bannerImageBytes = inputStream?.readBytes()
            }

            try {
                val user = ParseUser.getCurrentUser()
                val userDataPointer = user.getParseObject("userProfile")

                // Fetches the user profile from the BAck4App server
                userDataPointer?.fetchInBackground { userProfile: ParseObject?, fetchException: ParseException? ->
                    if (userProfile != null) {
                        // Updates profile image if available, otherwise removes it
                        if (profileImageBytes != null){
                            userProfile.put("profileImage", ParseFile("profile_image.jpg", profileImageBytes))
                        }else{
                            userProfile.remove("profileImage")
                        }

                        // Updates banner image if available, otherwise removes it
                        if (bannerImageBytes != null){
                            userProfile.put("bannerImage", ParseFile("banner_image.jpg", bannerImageBytes))
                        }else{
                            userProfile.remove("bannerImage")
                        }

                        // Updates bio text
                        userProfile.put("bioText", localUserProfile?.bioText ?: "")

                        // Convert favorite movies to JSON string and updates it
                        val favMovies = localUserProfile?.favouriteMovies ?: listOf(Movie(), Movie(), Movie(), Movie())
                        val jsonFavMovies = favMovies.toJson()
                        userProfile.put("favouriteMoviesString", jsonFavMovies)

                        // Saves changes to the user profile
                        userProfile.saveInBackground { saveException ->
                            if (saveException == null) {
                                Log.e("SocialViewModel", "Update profile successful")
                                onSuccess()
                            } else {
                                Log.e("SocialViewModel", "Error updating profile - saveException: ${saveException.message}")
                                onError("Error: ${saveException.message}")
                            }
                        }
                    }else{
                        Log.e("SocialViewModel", "Error user profile not found")
                        onError("Error: User profile not found")
                    }
                }
            }catch (e: Exception){
                Log.e("SocialViewModel", "Catch Error updating Profile: ", e)
                onError("Catch Error: ${e.message}")
            }
        }
    }
}

// Factory-class for creating SocialViewModel instances to manage access to the database
class SocialViewModelFactory(private val dao: UserProfileDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SocialViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SocialViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}