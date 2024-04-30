package com.patch4code.loglinemovieapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * RetrofitHelper - Utility class for managing retrofit instances for TMDB API
 * provides a method to obtain a Retrofit instance with a specific base URL
 *
 * Retrofit is a type-safe HTTP client for Android and the JVM
 * It simplifies the process of making HTTP requests and handling their responses
 * see: https://square.github.io/retrofit/
 *
 * @author Patch4Code
 */

object RetrofitHelper {
    fun getInstance(baseUrl: String): Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
