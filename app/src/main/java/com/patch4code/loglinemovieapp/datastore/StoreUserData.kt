package com.patch4code.loglinemovieapp.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class StoreUserData(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userData")
        val USER_ID_KEY = stringPreferencesKey("user_id")
        val SESSION_TOKEN_KEY = stringPreferencesKey("session_token")
        val EMAIL_KEY = stringPreferencesKey("email")
        val USERNAME_KEY = stringPreferencesKey("username")
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

    suspend fun saveUserData(userId: String, sessionToken: String, email: String, username: String){
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId
            preferences[SESSION_TOKEN_KEY] = sessionToken
            preferences[EMAIL_KEY] = email
            preferences[USERNAME_KEY] = username
        }
    }


}


