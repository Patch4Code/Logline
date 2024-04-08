package com.patch4code.loglinemovieapp.preferences_datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class StoreSettings(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
        val WATCH_PROVIDERS_COUNTRY_KEY = stringPreferencesKey("watch_providers_country")
    }

    val getWatchProvidersCountry: Flow<String?> = context.dataStore.data
        .map {preferences->
            preferences[WATCH_PROVIDERS_COUNTRY_KEY] ?: "US"
        }

    suspend fun setWatchProvidersCountry(watchProvidersCountry: String){
        context.dataStore.edit{ preferences ->
            preferences[WATCH_PROVIDERS_COUNTRY_KEY] = watchProvidersCountry
        }
    }
}