package com.patch4code.loglinemovieapp.features.list.presentation.components.lists_table.item

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.presentation.utils.MovieHelper
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieInList

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ListsItemPreviewImages - Composable function displaying the preview images for a movie list.
 * Shows up to 3 posters of the list, with the first having full with and the other two half width.
 *
 * @author Patch4Code
 */
@Composable
fun ListsItemPreviewImages(moviesInList :List<MovieInList>?){

    val firstMovie = moviesInList?.find { it.position == 0 }
    val secondMovie = moviesInList?.find { it.position == 1 }
    val thirdMovie = moviesInList?.find { it.position == 2 }

    AsyncImage(
        model = firstMovie?.posterUrl.let { MovieHelper.processPosterUrl(it) },
        contentDescription = null,
        error = painterResource(
            id = if (firstMovie != null) R.drawable.movie_poster_placeholder else R.drawable.no_movie_poster_placeholder
        )
    )
    AsyncImageCutPoster(url = secondMovie?.posterUrl?.let { MovieHelper.processPosterUrl(it) })
    AsyncImageCutPoster(url = thirdMovie?.posterUrl?.let { MovieHelper.processPosterUrl(it) })
}