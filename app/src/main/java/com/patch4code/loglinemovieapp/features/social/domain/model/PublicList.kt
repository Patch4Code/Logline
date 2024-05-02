package com.patch4code.loglinemovieapp.features.social.domain.model

import com.patch4code.loglinemovieapp.features.list.domain.model.MovieList
import java.util.Date

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * PublicList - Represents a public movie list.
 *
 * @author Patch4Code
 */
data class PublicList(
    val objectId: String,
    val userId: String,
    val publishedAt: Date,
    val authorName: String,
    val avatarPath: String,
    val movieList: MovieList,
    val isProfilePublic: Boolean
)
