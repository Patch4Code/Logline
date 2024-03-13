package com.patch4code.loglinemovieapp.features.movie.presentation.screen_movie

import androidx.lifecycle.ViewModel
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.domain.model.MovieUserData
import com.patch4code.loglinemovieapp.features.core.domain.model.userDataList
import com.patch4code.loglinemovieapp.features.diary.domain.model.LoggedMovie
import com.patch4code.loglinemovieapp.features.diary.domain.model.LoggedMoviesDummy

class MovieLogViewModel: ViewModel() {

    fun addMovieLog(movie: Movie, date: String, rating: Int, review: String){

        val loggedElement = LoggedMovie(movie = movie, date = date, rating = rating, review = review)

        //add to LoggedMovies (Dummy)
        LoggedMoviesDummy.add(loggedElement)

        updateRating(movie, rating)
        removeFromWatchlist(movie)
    }


    private fun updateRating(movie: Movie, rating: Int){

        val movieUserData = userDataList.find { it.movie?.id == movie.id }

        if (movieUserData != null){
            movieUserData.rating = rating
        }else{
            val newMovieUserData = MovieUserData(
                movie  = movie,
                onWatchlist = false,
                rating = rating
            )
            userDataList.add(newMovieUserData)
        }
    }


    private fun removeFromWatchlist(movie: Movie){
        val movieUserData = userDataList.find { it.movie?.id == movie.id }
        if (movieUserData != null){
            movieUserData.onWatchlist = false
        }
    }
}