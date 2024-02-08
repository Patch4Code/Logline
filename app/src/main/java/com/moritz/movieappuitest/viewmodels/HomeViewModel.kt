package com.moritz.movieappuitest.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moritz.movieappuitest.api.tmdb.RetrofitHelper
import com.moritz.movieappuitest.api.tmdb.TmdbApiService
import com.moritz.movieappuitest.dataclasses.Movie
import com.moritz.movieappuitest.utils.TmdbCredentials
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel(){

    private val tmdbApiService: TmdbApiService by lazy {
        RetrofitHelper.getInstance(TmdbCredentials.BASE_URL).create(TmdbApiService::class.java)
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _popularMovies = MutableLiveData<List<Movie>>()
    private val _topRatedMovies = MutableLiveData<List<Movie>>()
    private val _upcomingMovies = MutableLiveData<List<Movie>>()
    private val _homeMoviesMap = MutableLiveData<Map<String, List<Movie>>>()

    val homeMoviesMap: LiveData<Map<String, List<Movie>>>
        get() = _homeMoviesMap


    init {
        loadHomeViewData()
    }

    private fun updateHomeMovieMap() {
        val popularMovies = _popularMovies.value ?: emptyList()
        val topRatedMovies = _topRatedMovies.value ?: emptyList()
        val upcomingMovies = _upcomingMovies.value ?: emptyList()

        // Combine individual lists into a map
        val newMovieMap = mapOf(
            "Popular Movies" to popularMovies,
            "Top Rated Movies" to topRatedMovies,
            "Upcoming Movies" to upcomingMovies
        )

        _homeMoviesMap.value = newMovieMap
    }

    private fun loadHomeViewData(){
        viewModelScope.launch {
            try {
                _isLoading.value = true

                val popularResponse = tmdbApiService.getPopularMovies()
                if(popularResponse.isSuccessful){
                    _popularMovies.value = popularResponse.body()?.results
                }

                val topRatedResponse = tmdbApiService.getTopRated()
                if(topRatedResponse.isSuccessful){
                    _topRatedMovies.value = topRatedResponse.body()?.results
                }

                val upcomingResponse = tmdbApiService.getUpcoming()
                if(upcomingResponse.isSuccessful){
                    _upcomingMovies.value = upcomingResponse.body()?.results
                }
                _isLoading.value = false
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error loading data", e)
            } finally {
                updateHomeMovieMap()
            }
        }
    }
}