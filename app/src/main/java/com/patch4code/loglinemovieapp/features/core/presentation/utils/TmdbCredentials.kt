package com.patch4code.loglinemovieapp.features.core.presentation.utils

import com.patch4code.loglinemovieapp.BuildConfig

class TmdbCredentials {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"

        const val API_KEY = BuildConfig.apiKey

        const val POSTER_URL = "https://image.tmdb.org/t/p/w600_and_h900_bestv2"
        const val OTHER_IMAGE_URL = "https://image.tmdb.org/t/p/w200"
    }
}