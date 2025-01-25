package com.patch4code.logline.preferences_datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * StoreSettings - DataStore for storing and retrieving settings data using Preferences DataStore.
 * Provides functionality to save and retrieve settings data, such as watch providers country.
 *
 * @author Patch4Code
 */
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