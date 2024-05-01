package com.patch4code.loglinemovieapp.preferences_datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * StoreUserData - DataStore for storing and retrieving user data using Preferences DataStore.
 * Provides functionality to save and retrieve user-related data (related to the login for the
 * social features using Back4App), such as user ID, session token, email, username,and profile visibility.
 *
 * @author Patch4Code
 */
class StoreUserData(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userData")
        val USER_ID_KEY = stringPreferencesKey("user_id")
        val SESSION_TOKEN_KEY = stringPreferencesKey("session_token")
        val EMAIL_KEY = stringPreferencesKey("email")
        val USERNAME_KEY = stringPreferencesKey("username")
        val PROFILE_PUBLIC_KEY = booleanPreferencesKey("is_profile_public")
    }

    val getUserId: Flow<String?> = context.dataStore.data
        .map {preferences->
        preferences[USER_ID_KEY] ?: ""
        }
    val getSessionToken: Flow<String?> = context.dataStore.data
        .map {preferences->
            preferences[SESSION_TOKEN_KEY] ?: ""
        }
    val getEmail: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[EMAIL_KEY] ?: ""
        }
    val getUsername: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USERNAME_KEY] ?: ""
        }

    val getIsProfilePublic: Flow<Boolean?> = context.dataStore.data
        .map { preferences ->
            preferences[PROFILE_PUBLIC_KEY] ?: false
        }

    suspend fun saveUserData(userId: String, sessionToken: String, email: String, username: String){
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId
            preferences[SESSION_TOKEN_KEY] = sessionToken
            preferences[EMAIL_KEY] = email
            preferences[USERNAME_KEY] = username
        }
    }

    suspend fun setProfilePublicState(isProfilePublic: Boolean){
        context.dataStore.edit{ preferences ->
            preferences[PROFILE_PUBLIC_KEY] = isProfilePublic
        }
    }

    suspend fun deleteUserData(){
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = ""
            preferences[SESSION_TOKEN_KEY] = ""
            preferences[EMAIL_KEY] = ""
            preferences[USERNAME_KEY] = ""
        }
    }
}


