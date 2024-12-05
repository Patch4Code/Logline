package com.patch4code.loglinemovieapp.features.core.presentation.utils

import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieInList

object MovieInListMapper {
    fun mapToMovieInListFromMovie(movieListId: String, position: Int, timeAdded: Long, movie: Movie?): MovieInList {
        return MovieInList(
            movieListId = movieListId,
            position = position,
            movieId = movie?.id ?: -1,
            title = movie?.title ?: "N/A",
            releaseDate = movie?.releaseDate ?: "N/A-date",
            posterUrl = movie?.posterUrl.orEmpty(),
            timeAdded = timeAdded,
            genreIds = movie?.genreIds ?: emptyList(),
            originalLanguage = movie?.originalLanguage ?: "N/A",
            originalTitle = movie?.originalTitle ?: "N/A",
            popularity = movie?.popularity ?: 0.0,
            voteAverage = movie?.voteAverage ?: 0.0
        )
    }
}