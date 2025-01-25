package com.patch4code.logline.features.core.domain.model

import androidx.room.Embedded

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MovieWithUserData - Data class combining a Movie entity with its associated user-specific data.
 * This class is used to fetch data from the database, combining information stored in separate
 * database tables (Movie and MovieUserData) into a single unified object.
 *
 * @author Patch4Code
 */
data class MovieWithUserData(
    @Embedded val movie: Movie,
    @Embedded val userData: MovieUserData
)
