package com.patch4code.loglinemovieapp.features.core.presentation.utils

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * TmdbCredentials - Helper class containing credentials and base URLs for TMDB API
 *
 * @author Patch4Code
 */

class TmdbCredentials {
    companion object {
        private const val ENC_API_KEY = "M2U3Mjk0NGM4ZGE4ZTI0NGMxOGE1ZjgxZTk1NWU4OWY="

        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val POSTER_URL = "https://image.tmdb.org/t/p/w600_and_h900_bestv2"
        const val OTHER_IMAGE_URL = "https://image.tmdb.org/t/p/w200"

        fun getApiKey(): String {
            return Decoder.decodeKey(ENC_API_KEY)
        }
    }
}
