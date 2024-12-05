package com.patch4code.loglinemovieapp.features.core.presentation.utils

import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.movie.domain.model.MovieDetails
import com.patch4code.loglinemovieapp.features.person_details.domain.model.MovieAsCastMember
import com.patch4code.loglinemovieapp.features.person_details.domain.model.MovieAsCrewMember

object MovieMapper {
    fun mapToMovie(data: Any?): Movie {
        return when (data) {
            is MovieDetails -> mapToMovieFromMovieDetails(data)
            is MovieAsCastMember -> mapToMovieFromCastMember(data)
            is MovieAsCrewMember -> mapToMovieFromCrewMember(data)
            else -> throw IllegalArgumentException("Unsupported type for mapping")
        }
    }

    private fun mapToMovieFromMovieDetails(movieData: MovieDetails?): Movie {
        return Movie(
            title = movieData?.title ?: "N/A",
            id = movieData?.id ?: -1,
            releaseDate = movieData?.releaseDate ?: "N/A",
            posterUrl = movieData?.posterPath ?: "",
            genreIds = movieData?.genres?.map { it.id } ?: emptyList(),
            originalLanguage = movieData?.originalLanguage ?: "N/A",
            originalTitle = movieData?.originalTitle ?: "N/A",
            popularity = movieData?.popularity ?: 0.0,
            voteAverage = movieData?.voteAverage ?: 0.0
        )
    }

    private fun mapToMovieFromCastMember(movieData: MovieAsCastMember?): Movie {
        return Movie(
            title = movieData?.title ?: "N/A",
            id = movieData?.id ?: -1,
            releaseDate = movieData?.releaseDate ?: "N/A",
            posterUrl = movieData?.posterUrl ?: "",
            genreIds = movieData?.genreIds ?: emptyList(),
            originalLanguage = movieData?.originalLanguage ?: "N/A",
            originalTitle = movieData?.originalTitle ?: "N/A",
            popularity = movieData?.popularity ?: 0.0,
            voteAverage = movieData?.voteAverage ?: 0.0
        )
    }

    private fun mapToMovieFromCrewMember(movieData: MovieAsCrewMember?): Movie {
        return Movie(
            title = movieData?.title ?: "N/A",
            id = movieData?.id ?: -1,
            releaseDate = movieData?.releaseDate ?: "N/A",
            posterUrl = movieData?.posterUrl ?: "",
            genreIds = movieData?.genreIds ?: emptyList(),
            originalLanguage = movieData?.originalLanguage ?: "N/A",
            originalTitle = movieData?.originalTitle ?: "N/A",
            popularity = movieData?.popularity ?: 0.0,
            voteAverage = movieData?.voteAverage ?: 0.0
        )
    }
}