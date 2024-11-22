package com.patch4code.loglinemovieapp

import android.app.Application
import com.parse.Parse
import com.patch4code.loglinemovieapp.features.core.presentation.utils.Back4AppCredentials

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * App - Application class for initializing Parse SDK for he use of Back4App.
 *
 * @author Patch4Code
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId(Back4AppCredentials.getAppId())
                .clientKey(Back4AppCredentials.getClientKey())
                .server(Back4AppCredentials.BACK4APP_SERVER_URL)
                .build())
    }
}