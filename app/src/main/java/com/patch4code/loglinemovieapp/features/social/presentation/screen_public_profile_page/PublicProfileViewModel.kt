package com.patch4code.loglinemovieapp.features.social.presentation.screen_public_profile_page

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.reflect.TypeToken
import com.parse.ParseObject
import com.parse.ParseQuery
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.presentation.utils.JSONHelper
import com.patch4code.loglinemovieapp.features.profile.domain.model.UserProfile
import kotlinx.coroutines.launch
import java.lang.reflect.Type

class PublicProfileViewModel: ViewModel() {


    private val _userProfileData = MutableLiveData<UserProfile>()
    val userProfileData: LiveData<UserProfile> get() = _userProfileData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun getPublicUserProfile(userId: String?){
        viewModelScope.launch {
            if (userId == null) return@launch
            try {
                _isLoading.value = true

                val query = ParseQuery.getQuery<ParseObject>("UserProfile")
                query.whereEqualTo("user", ParseObject.createWithoutData("_User", userId))

                query.getFirstInBackground { publicProfile, e ->
                    if (e != null) {
                        Log.e("PublicProfileViewModel", "Error loading public profile: ", e)
                    }else{
                        val userName = publicProfile.getString("userName") ?: "Anonymous"
                        val profileImage = publicProfile.getParseFile("profileImage")
                        val bannerImage = publicProfile.getParseFile("bannerImage")
                        val bioText = publicProfile.getString("bioText") ?: ""

                        val favMoviesJsonString = publicProfile.getString("favouriteMoviesString")
                        val type: Type = object : TypeToken<List<Movie>>() {}.type
                        val favMovies: List<Movie> = JSONHelper.fromJsonWithType(favMoviesJsonString, type)

                        val publicUserProfile = UserProfile(
                            username = userName,
                            profileImagePath = profileImage?.url ?: "",
                            bannerImagePath = bannerImage?.url ?: "",
                            bioText = bioText,
                            favouriteMovies = favMovies
                        )
                        _userProfileData.value = publicUserProfile
                        _isLoading.value = false
                    }
                }
            }catch (e: Exception){
                Log.e("PublicProfilesViewModel", "Error getting public UserProfiles", e)
            }
        }
    }

}