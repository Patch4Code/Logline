package com.moritz.movieappuitest.utils

import com.moritz.movieappuitest.BuildConfig

class TmdbCredentials {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"

        const val API_KEY = BuildConfig.apiKey

        const val POSTER_URL = "https://image.tmdb.org/t/p/w600_and_h900_bestv2"
    }
}