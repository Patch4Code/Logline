package com.patch4code.logline.features.list.domain.model

import androidx.room.Embedded
import com.patch4code.logline.features.core.domain.model.Movie

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MovieWithListItem - Data class combining a Movie entity with its associated movieInList data.
 * This class is used to fetch data from the database, combining information stored in separate
 * database tables (Movie and MovieInList) into a single unified object.
 *
 * @author Patch4Code
 */
data class MovieWithListItem(
    @Embedded val movie: Movie,
    @Embedded val movieInList: MovieInList
)
