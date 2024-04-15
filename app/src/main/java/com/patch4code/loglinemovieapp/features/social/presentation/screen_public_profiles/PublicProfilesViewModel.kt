package com.patch4code.loglinemovieapp.features.social.presentation.screen_public_profiles

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.reflect.TypeToken
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.presentation.utils.JSONHelper
import com.patch4code.loglinemovieapp.features.social.domain.model.PublicUserProfile
import kotlinx.coroutines.launch
import java.lang.reflect.Type

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
                        val publicProfilesList = mutableListOf<PublicUserProfile>()
                        for (profile in publicProfiles){
                            val userName = profile.getString("userName")
                            val userId = profile.getParseUser("user")?.objectId
                            val profileImage = profile.getParseFile("profileImage")
                            val bannerImage = profile.getParseFile("bannerImage")
                            val bioText = profile.getString("bioText")

                            val favMoviesJsonString = profile.getString("favouriteMoviesString")
                            val type: Type = object : TypeToken<List<Movie>>() {}.type
                            val favMovies: List<Movie> = JSONHelper.fromJsonWithType(favMoviesJsonString, type)

                            val userProfile = PublicUserProfile(
                                userId = userId ?: "",
                                username = userName ?: "Anonymous User",
                                profileImagePath = profileImage?.url ?: "",
                                bannerImagePath = bannerImage?.url ?: "",
                                bioText = bioText ?: "",
                                favouriteMovies = favMovies,
                            )
                            publicProfilesList.add(userProfile)
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