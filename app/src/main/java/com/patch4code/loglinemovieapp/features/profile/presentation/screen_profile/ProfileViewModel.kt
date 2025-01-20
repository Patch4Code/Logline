package com.patch4code.loglinemovieapp.features.profile.presentation.screen_profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.profile.domain.model.UserProfile
import com.patch4code.loglinemovieapp.features.profile.domain.model.UserProfileWithFavouriteMovies
import com.patch4code.loglinemovieapp.room_database.UserProfileDao
import kotlinx.coroutines.launch

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ProfileViewModel - ViewModel responsible for managing user profile data.
 *
 * @property dao UserProfileDao for accessing user profile data from the db.
 * @author Patch4Code
 */
class ProfileViewModel(private val dao: UserProfileDao): ViewModel() {

    private val _userProfileData = MutableLiveData<UserProfileWithFavouriteMovies>()
    val userProfileData: LiveData<UserProfileWithFavouriteMovies> get() = _userProfileData

    // Retrieves user profile data from the database.
    // If no user profile exists yet, create a new profile.
    fun getUserProfileData(){
        viewModelScope.launch {
            val tempUserProfile = dao.getUserProfileWithFavouriteMovies()
            if(tempUserProfile == null){
                val defaultEmptyFavMovies = List(4) { null as Movie? }
                dao.upsertUserProfile(UserProfile())
                _userProfileData.value = UserProfileWithFavouriteMovies(UserProfile(), defaultEmptyFavMovies)
            }else{
                _userProfileData.value = tempUserProfile!!
            }
            //Log.e("ProfileViewModel", "_userProfileData: ${_userProfileData.value}")
        }
    }

    // Updates the profile name in the db
    fun updateProfileName(profileName: String){
        viewModelScope.launch {
            dao.updateProfileName(profileName)
            _userProfileData.value = dao.getUserProfileWithFavouriteMovies()
        }
    }

    // Updates the bio text in the db
    fun updateBioText(bioText: String){
        viewModelScope.launch {
            dao.updateBio(bioText)
            _userProfileData.value = dao.getUserProfileWithFavouriteMovies()
        }
    }

    // Sets the profile image path in the db
    fun setProfileImagePath(path: String){
        viewModelScope.launch {
            dao.setProfileImagePath(path)
            _userProfileData.value = dao.getUserProfileWithFavouriteMovies()
        }
    }

    // Sets the banner image path in the db
    fun setBannerImagePath(path: String){
        viewModelScope.launch {
            dao.setBannerImagePath(path)
            _userProfileData.value = dao.getUserProfileWithFavouriteMovies()
        }
    }

    // Sets a favorite movie at a specified index in the db
    fun setFavMovieAtIndex(index: Int, movie: Movie?){
        viewModelScope.launch {
            if (movie != null){
                dao.setFavMovieAtIndex(index, movie)
            } else{
                dao.deleteFavMovieAtIndex(index)
            }

            _userProfileData.value = dao.getUserProfileWithFavouriteMovies()
        }
    }
}

// Factory-class for creating ProfileViewModel instances to manage access to the database
class ProfileViewModelFactory(private val dao: UserProfileDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}