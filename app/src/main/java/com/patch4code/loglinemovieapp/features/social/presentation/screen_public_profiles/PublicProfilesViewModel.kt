package com.patch4code.loglinemovieapp.features.social.presentation.screen_public_profiles

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.presentation.utils.JSONHelper
import com.patch4code.loglinemovieapp.features.social.domain.model.PublicUserProfile
import kotlinx.coroutines.launch

class PublicProfilesViewModel: ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _publicUserProfiles = MutableLiveData<List<PublicUserProfile>>()
    val publicUserProfiles: LiveData<List<PublicUserProfile>> get() = _publicUserProfiles

    fun getPublicUserProfiles(){
        viewModelScope.launch {
            try {
                _isLoading.value = true

                val currentUser = ParseUser.getCurrentUser()

                val query = ParseQuery.getQuery<ParseObject>("UserProfile")
                query.whereEqualTo("isPublic", true)
                query.whereNotEqualTo("user", currentUser)

                query.findInBackground { publicProfiles, e ->
                    if (publicProfiles.isNotEmpty()) {
                        //Log.e("PublicProfilesViewModel", "publicProfiles.isNotEmpty(): $publicProfiles")
                        val publicProfilesList = mutableListOf<PublicUserProfile>()
                        for (profile in publicProfiles){
                            val userName = profile.getString("userName")
                            val userId = profile.getParseUser("user")?.objectId
                            val profileImage = profile.getParseFile("profileImage")
                            val bannerImage = profile.getParseFile("bannerImage")
                            val bioText = profile.getString("bioText")

                            val favMoviesJsonString = profile.getString("favouriteMoviesString")
                            Log.e("PublicProfilesViewModel", "favMoviesJsonString: $favMoviesJsonString")
                            var favMovies: List<Movie> = JSONHelper.fromJson(favMoviesJsonString)

                            //problem json conversion id get to 11.0 etc.



                            val profile = PublicUserProfile(
                                userId = userId ?: "",
                                username = userName ?: "Anonymous User",
                                profileImagePath = profileImage?.url ?: "",
                                bannerImagePath = bannerImage?.url ?: "",
                                bioText = bioText ?: "",
                                favouriteMovies = favMovies,
                            )
                            publicProfilesList.add(profile)
                        }

                        _publicUserProfiles.value = publicProfilesList
                        _isLoading.value = false
                    } else {
                        Log.e("PublicProfilesViewModel", "Error")
                    }
                }
            }catch (e: Exception){
                Log.e("PublicProfilesViewModel", "Error getting public UserProfiles", e)
            }
        }
    }
}