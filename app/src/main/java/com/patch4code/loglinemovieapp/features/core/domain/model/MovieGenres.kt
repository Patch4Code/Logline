package com.patch4code.loglinemovieapp.features.core.domain.model

import android.content.Context
import com.patch4code.loglinemovieapp.features.core.presentation.utils.StringResourceHelper.getStringResourceFromName

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MovieGenres - Provides mappings of genre number (from tmdb) to the genre name.
 *
 * @author Patch4Code
 */

object MovieGenres {
    fun getAllGenres(context: Context): Map<Int, String>{
        return mapOf(
            28 to getStringResourceFromName(context, "genre_28"),
            12 to getStringResourceFromName(context, "genre_12"),
            16 to getStringResourceFromName(context, "genre_16"),
            35 to getStringResourceFromName(context, "genre_35"),
            80 to getStringResourceFromName(context, "genre_80"),
            99 to getStringResourceFromName(context, "genre_99"),
            18 to getStringResourceFromName(context, "genre_18"),
            10751 to getStringResourceFromName(context, "genre_10751"),
            14 to getStringResourceFromName(context, "genre_14"),
            36 to getStringResourceFromName(context, "genre_36"),
            27 to getStringResourceFromName(context, "genre_27"),
            10402 to getStringResourceFromName(context, "genre_10402"),
            9648 to getStringResourceFromName(context, "genre_9648"),
            10749 to getStringResourceFromName(context, "genre_10749"),
            878 to getStringResourceFromName(context, "genre_878"),
            53 to getStringResourceFromName(context, "genre_53"),
            10770 to getStringResourceFromName(context, "genre_10770"),
            10752 to getStringResourceFromName(context, "genre_10752"),
            37 to getStringResourceFromName(context, "genre_37")
        )
    }
}