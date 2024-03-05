package com.moritz.movieappuitest.features.list.presentation.components.lists_table.item

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.moritz.movieappuitest.R
import com.moritz.movieappuitest.features.list.domain.model.MovieList
import com.moritz.movieappuitest.features.core.presentation.utils.MovieHelper

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