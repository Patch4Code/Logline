package com.moritz.movieappuitest.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moritz.movieappuitest.api.tmdb.RetrofitHelper
import com.moritz.movieappuitest.api.tmdb.TmdbApiService
import com.moritz.movieappuitest.dataclasses.Movie
import com.moritz.movieappuitest.dataclasses.MovieCredits
import com.moritz.movieappuitest.dataclasses.MovieDetails
import com.moritz.movieappuitest.utils.TmdbCredentials
import kotlinx.coroutines.launch

class MovieViewModel: ViewModel(){

    private val tmdbApiService: TmdbApiService by lazy {
        RetrofitHelper.getInstance(TmdbCredentials.BASE_URL).create(TmdbApiService::class.java)
    }

    private val _detailsData = MutableLiveData<MovieDetails>()
    val detailsData: LiveData<MovieDetails>
        get() = _detailsData

    private val _creditsData = MutableLiveData<MovieCredits>()
    val creditsData: LiveData<MovieCredits>
        get() = _creditsData

    private val _collectionMovies = MutableLiveData<List<Movie>>()
    val collectionMovies: LiveData<List<Movie>>
        get() = _collectionMovies


    fun loadMovieDetails(movieId: Int){
        viewModelScope.launch {
            try {
                val movieDetailsResponse = tmdbApiService.getMovieDetails(movieId = movieId)
                if(movieDetailsResponse.isSuccessful){
                    _detailsData.value = movieDetailsResponse.body()
                }
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error getting movie details", e)
            }
        }
    }

    fun loadMovieCredits(movieId: Int){
        viewModelScope.launch {
            try {
                val movieCreditsResponse = tmdbApiService.getMovieCredits(movieId = movieId)
                if(movieCreditsResponse.isSuccessful){
                    _creditsData.value = movieCreditsResponse.body()
                }
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error getting movie credits", e)
            }
        }
    }

    fun loadMovieCollection(collectionId: Int){
        Log.e("MovieViewModel","collectionId: $collectionId")
        viewModelScope.launch {
            try {
                val movieCollectionResponse = tmdbApiService.getMoviesFromCollection(collectionId = collectionId)
                if(movieCollectionResponse.isSuccessful){
                    _collectionMovies.value = movieCollectionResponse.body()?.movies
                }
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error getting movie collection", e)
            }
        }
    }
}