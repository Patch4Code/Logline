package com.patch4code.loglinemovieapp

import android.app.Application
import com.parse.Parse

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
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build())
    }
}