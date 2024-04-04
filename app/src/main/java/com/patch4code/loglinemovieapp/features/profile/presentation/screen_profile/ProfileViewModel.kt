package com.patch4code.loglinemovieapp.features.profile.presentation.screen_profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.patch4code.loglinemovieapp.features.profile.domain.model.UserProfile
import com.patch4code.loglinemovieapp.room_database.UserProfileDao
import kotlinx.coroutines.launch

class ProfileViewModel(private val dao: UserProfileDao): ViewModel() {

    private val _userProfileData = MutableLiveData<UserProfile>()
    val userProfileData: LiveData<UserProfile> get() = _userProfileData

    fun getUserProfileData(){
        viewModelScope.launch {
            val tempUserProfile = dao.getUserProfile()
            if(tempUserProfile == null){
                dao.upsertUserProfile(UserProfile())
                _userProfileData.value = UserProfile()
            }else{
                _userProfileData.value = tempUserProfile!!
            }
        }
    }

    fun updateProfileName(profileName: String){
        viewModelScope.launch {
            dao.updateProfileName(profileName)
            _userProfileData.value = dao.getUserProfile()
        }
    }

    fun updateBioText(bioText: String){
        viewModelScope.launch {
            dao.updateBio(bioText)
            _userProfileData.value = dao.getUserProfile()
        }
    }
}


class ProfileViewModelFactory(private val dao: UserProfileDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}