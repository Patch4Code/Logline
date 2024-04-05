package com.patch4code.loglinemovieapp.room_database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.patch4code.loglinemovieapp.features.profile.domain.model.UserProfile

@Dao
interface UserProfileDao {

    @Upsert
    suspend fun upsertUserProfile(userProfile: UserProfile)

    @Query("SELECT * From UserProfile WHERE id = 1 LIMIT 1")
    suspend fun getUserProfile(): UserProfile?

    @Transaction
    suspend fun updateProfileName(profileName: String){
        val userProfileUpdate = getUserProfile()
        if (userProfileUpdate != null) {
            userProfileUpdate.username = profileName
            upsertUserProfile(userProfileUpdate)
        }
    }

    @Transaction
    suspend fun updateBio(bioText: String){
        val userProfileUpdate = getUserProfile()
        if (userProfileUpdate != null){
            userProfileUpdate.bioText = bioText
            upsertUserProfile(userProfileUpdate)
        }
    }

    @Transaction
    suspend fun setProfileImagePath(path: String){
        val userProfileUpdate = getUserProfile()
        if (userProfileUpdate != null) {
            userProfileUpdate.profileImagePath = path
            upsertUserProfile(userProfileUpdate)
        }
    }

    @Transaction
    suspend fun setBannerImagePath(path: String){
        val userProfileUpdate = getUserProfile()
        if (userProfileUpdate != null) {
            userProfileUpdate.bannerImagePath = path
            upsertUserProfile(userProfileUpdate)
        }
    }
}