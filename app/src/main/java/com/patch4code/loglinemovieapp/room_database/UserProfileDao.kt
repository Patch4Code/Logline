package com.patch4code.loglinemovieapp.room_database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.patch4code.loglinemovieapp.features.profile.domain.model.UserProfile

@Dao
interface UserProfileDao {

    @Upsert
    suspend fun upsertUserProfile(userProfile: UserProfile)

    @Query("SELECT * From UserProfile WHERE id = 1 LIMIT 1")
    suspend fun getUserProfile(): UserProfile?
}