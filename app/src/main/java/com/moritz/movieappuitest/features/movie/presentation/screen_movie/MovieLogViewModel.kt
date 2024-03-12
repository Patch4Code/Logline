package com.moritz.movieappuitest.features.movie.presentation.screen_movie

import androidx.lifecycle.ViewModel
import com.moritz.movieappuitest.features.core.domain.model.Movie
import com.moritz.movieappuitest.features.diary.domain.model.LoggedMovie
import com.moritz.movieappuitest.features.diary.domain.model.LoggedMoviesDummy

class MovieLogViewModel: ViewModel() {

    fun addMovieLog(movie: Movie, date: String, rating: Int, review: String){

        val loggedElement = LoggedMovie(movie = movie, date = date, rating = rating, review = review)

        //add to LoggedMovies (Dummy)
        LoggedMoviesDummy.add(loggedElement)
    }
}