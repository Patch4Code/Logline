package com.patch4code.loglinemovieapp.features.diary.domain.model

import androidx.room.Embedded
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MovieWithLoggedData - Data class combining a Movie entity with its associated log-data.
 * This class is used to fetch data from the database, combining information stored in separate
 * database tables (Movie and LoggedMovie) into a single unified object.
 *
 * @author Patch4Code
 */
data class MovieWithLoggedData(
    @Embedded val movie: Movie,
    @Embedded val loggedMovie: LoggedMovie
)
