package com.patch4code.loglinemovieapp.features.home.presentation.screen_home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patch4code.loglinemovieapp.api.RetrofitHelper
import com.patch4code.loglinemovieapp.api.TmdbApiService
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.presentation.utils.TmdbCredentials
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


    fun loadHomeViewData(homeViewTitles: Array<String>){
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
                updateHomeMovieMap(homeViewTitles)
            }
        }
    }


    private fun updateHomeMovieMap(homeViewTitles: Array<String>) {
        val popularMovies = _popularMovies.value ?: emptyList()
        val topRatedMovies = _topRatedMovies.value ?: emptyList()
        val upcomingMovies = _upcomingMovies.value ?: emptyList()

        // Combine individual lists into a map
        val newMovieMap = mapOf(
            homeViewTitles[0] to popularMovies,
            homeViewTitles[1] to topRatedMovies,
            homeViewTitles[2] to upcomingMovies
        )
        _homeMoviesMap.value = newMovieMap
    }
}