package com.patch4code.loglinemovieapp.features.core.presentation.utils

import com.patch4code.loglinemovieapp.BuildConfig

/**
 * APACHE LICENSE, VERSION 2.0 (https://www.apache.org/licenses/LICENSE-2.0)
 *
 * TmdbCredentials - Helper class containing credentials and base URLs for TMDB API
 *
 * @author Patch4Code
 */

class TmdbCredentials {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"

        const val API_KEY = BuildConfig.apiKey

        const val POSTER_URL = "https://image.tmdb.org/t/p/w600_and_h900_bestv2"
        const val OTHER_IMAGE_URL = "https://image.tmdb.org/t/p/w200"
    }
}