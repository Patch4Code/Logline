package com.patch4code.logline.features.list.presentation.components.lists_table.item

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.patch4code.logline.R
import com.patch4code.logline.features.core.presentation.utils.MovieHelper
import com.patch4code.logline.features.list.domain.model.MovieWithListItem

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ListsItemPreviewImages - Composable function displaying the preview images for a movie list.
 * Shows up to 3 posters of the list, with the first having full with and the other two half width.
 *
 * @author Patch4Code
 */
@Composable
fun ListsItemPreviewImages(moviesWithListItems :List<MovieWithListItem>?){

    val firstMovie = moviesWithListItems?.find { it.movieInList.position == 0 }
    val secondMovie = moviesWithListItems?.find { it.movieInList.position == 1 }
    val thirdMovie = moviesWithListItems?.find { it.movieInList.position == 2 }

    AsyncImage(
        model = firstMovie?.movie?.posterUrl.let { MovieHelper.processPosterUrl(it) },
        contentDescription = null,
        error = painterResource(
            id = if (firstMovie != null) R.drawable.movie_poster_placeholder else R.drawable.no_movie_poster_placeholder
        )
    )
    AsyncImageCutPoster(url = secondMovie?.movie?.posterUrl?.let { MovieHelper.processPosterUrl(it) })
    AsyncImageCutPoster(url = thirdMovie?.movie?.posterUrl?.let { MovieHelper.processPosterUrl(it) })
}