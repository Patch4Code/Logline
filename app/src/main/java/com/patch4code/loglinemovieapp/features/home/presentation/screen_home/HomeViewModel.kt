package com.patch4code.loglinemovieapp.features.home.presentation.screen_home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.api.RetrofitHelper
import com.patch4code.loglinemovieapp.api.TmdbApiService
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.presentation.utils.TmdbCredentials
import com.patch4code.loglinemovieapp.features.core.presentation.utils.UiText
import kotlinx.coroutines.launch

/**
 * APACHE LICENSE, VERSION 2.0 (https://www.apache.org/licenses/LICENSE-2.0)
 *
 * HomeViewModel - ViewModel responsible for managing data related to the home screen.
 * Loads data for popular, top-rated, and upcoming movies from the TMDB API.
 *
 * @author Patch4Code
 */
class HomeViewModel : ViewModel(){

    private val tmdbApiService: TmdbApiService by lazy {
        RetrofitHelper.getInstance(TmdbCredentials.BASE_URL).create(TmdbApiService::class.java)
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _popularMovies = MutableLiveData<List<Movie>>()
    private val _topRatedMovies = MutableLiveData<List<Movie>>()
    private val _upcomingMovies = MutableLiveData<List<Movie>>()

    private val _homeMoviesMap = MutableLiveData<Map<UiText.StringResource, List<Movie>>>()
    val homeMoviesMap: LiveData<Map<UiText.StringResource, List<Movie>>> get() = _homeMoviesMap

    // Loads data for popular, top-rated, and upcoming movies from the TMDB API and updates loading status
    fun loadHomeViewData(){
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

    // creates homeMoviesMap from the loaded data
    private fun updateHomeMovieMap() {
        val popularMovies = _popularMovies.value ?: emptyList()
        val topRatedMovies = _topRatedMovies.value ?: emptyList()
        val upcomingMovies = _upcomingMovies.value ?: emptyList()

        // Combine individual lists into a map
        val newMovieMap = mapOf(
            UiText.StringResource(R.string.popular_movies_title) to popularMovies,
            UiText.StringResource(R.string.top_rated_movies_title) to topRatedMovies,
            UiText.StringResource(R.string.upcoming_movies_title) to upcomingMovies
        )
        _homeMoviesMap.value = newMovieMap
    }
}