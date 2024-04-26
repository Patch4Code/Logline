package com.patch4code.loglinemovieapp.features.list.presentation.components.lists_table.item

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.core.presentation.utils.MovieHelper
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieList

/**
 * APACHE LICENSE, VERSION 2.0 (https://www.apache.org/licenses/LICENSE-2.0)
 *
 * ListsItemPreviewImages - Composable function displaying the preview images for a movie list.
 * Shows up to 3 posters of the list, with the first having full with and the other two half width.
 *
 * @author Patch4Code
 */
@Composable
fun ListsItemPreviewImages(list: MovieList){

    AsyncImage(
        model = if(list.movies.isNotEmpty()) MovieHelper.processPosterUrl(list.movies[0].posterUrl) else null,
        contentDescription = null,
        error = painterResource(id = R.drawable.no_movie_poster_placeholder)
    )
    AsyncImageCutPoster(url = if (list.movies.size >= 2) MovieHelper.processPosterUrl(list.movies[1].posterUrl) else null)
    AsyncImageCutPoster(url = if (list.movies.size >= 3) MovieHelper.processPosterUrl(list.movies[2].posterUrl) else null)
}