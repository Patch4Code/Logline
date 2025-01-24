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
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
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

    private val _hasLoadError = MutableLiveData<Boolean>()
    val hasLoadError: LiveData<Boolean> get() = _hasLoadError

    private val _popularMovies = MutableLiveData<List<Movie>>()
    private var popularMoviesHighestLoadedPage = 1
    private var popularMoviesPageAmount = 1

    private val _topRatedMovies = MutableLiveData<List<Movie>>()
    private var topRatedMoviesHighestLoadedPage = 1
    private var topRatedMoviesPageAmount = 1

    private val _upcomingMovies = MutableLiveData<List<Movie>>()
    private var upcomingMoviesHighestLoadedPage = 1
    private var upcomingMoviesPageAmount = 1



    private val _homeMoviesMap = MutableLiveData<Map<UiText.StringResource, List<Movie>>>()
    val homeMoviesMap: LiveData<Map<UiText.StringResource, List<Movie>>> get() = _homeMoviesMap

    // Loads data for popular, top-rated, and upcoming movies from the TMDB API and updates loading status
    fun loadHomeViewData(){

        if (_popularMovies.value.isNullOrEmpty() &&
            _topRatedMovies.value.isNullOrEmpty() &&
            _upcomingMovies.value.isNullOrEmpty()
        ) {
            viewModelScope.launch {
                try {
                    _isLoading.value = true
                    _hasLoadError.value = false

                    val popularResponse = tmdbApiService.getPopularMovies()
                    if(popularResponse.isSuccessful){
                        _popularMovies.value = popularResponse.body()?.results
                        popularMoviesPageAmount = popularResponse.body()?.totalPages ?: 1
                    }

                    val upcomingResponse = tmdbApiService.getUpcoming()
                    if(upcomingResponse.isSuccessful){
                        _upcomingMovies.value = upcomingResponse.body()?.results
                        upcomingMoviesPageAmount = upcomingResponse.body()?.totalPages ?: 1
                    }

                    val topRatedResponse = tmdbApiService.getTopRated()
                    if(topRatedResponse.isSuccessful){
                        _topRatedMovies.value = topRatedResponse.body()?.results
                        topRatedMoviesPageAmount = topRatedResponse.body()?.totalPages ?: 1
                    }

                    _isLoading.value = false
                    updateHomeMovieMap()
                } catch (e: Exception) {
                    _isLoading.value = false
                    _hasLoadError.value = true
                    Log.e("HomeViewModel", "Error loading data", e)
                }
            }
        }
    }

    // creates homeMoviesMap from the loaded data
    private fun updateHomeMovieMap() {
        val popularMovies = _popularMovies.value ?: emptyList()
        val upcomingMovies = _upcomingMovies.value ?: emptyList()
        val topRatedMovies = _topRatedMovies.value ?: emptyList()


        // Combine individual lists into a map
        val newMovieMap = mapOf(
            UiText.StringResource(R.string.popular_movies_title) to popularMovies,
            UiText.StringResource(R.string.upcoming_movies_title) to upcomingMovies,
            UiText.StringResource(R.string.top_rated_movies_title) to topRatedMovies
        )
        _homeMoviesMap.value = newMovieMap
    }

    fun loadMoreMovies(groupName: UiText. StringResource){
        viewModelScope.launch {
            try {
                when (groupName.resId) {
                    UiText.StringResource(R.string.popular_movies_title).resId -> {
                        if(popularMoviesHighestLoadedPage < popularMoviesPageAmount){
                            val nextPage = popularMoviesHighestLoadedPage + 1
                            val response = tmdbApiService.getPopularMovies(page = nextPage)
                            if (response.isSuccessful) {
                                val newMovies = response.body()?.results.orEmpty()
                                _popularMovies.value = (_popularMovies.value.orEmpty() + newMovies)
                                popularMoviesHighestLoadedPage = nextPage
                                topRatedMoviesHighestLoadedPage = nextPage
                            }
                        }
                    }
                    UiText.StringResource(R.string.upcoming_movies_title).resId -> {
                        if(upcomingMoviesHighestLoadedPage < upcomingMoviesPageAmount){
                            val nextPage = upcomingMoviesHighestLoadedPage + 1
                            val response = tmdbApiService.getUpcoming(page = nextPage)
                            if (response.isSuccessful) {
                                val newMovies = response.body()?.results.orEmpty()
                                _upcomingMovies.value = (_upcomingMovies.value.orEmpty() + newMovies)
                                upcomingMoviesHighestLoadedPage = nextPage
                            }
                        }
                    }
                    UiText.StringResource(R.string.top_rated_movies_title).resId -> {
                        if(topRatedMoviesHighestLoadedPage < topRatedMoviesPageAmount){
                            val nextPage = topRatedMoviesHighestLoadedPage + 1
                            val response = tmdbApiService.getTopRated(page = nextPage)
                            if (response.isSuccessful) {
                                val newMovies = response.body()?.results.orEmpty()
                                _topRatedMovies.value = (_topRatedMovies.value.orEmpty() + newMovies)
                                topRatedMoviesHighestLoadedPage = nextPage
                            }
                        }
                    }
                    else -> {
                        Log.e("HomeViewModel", "Invalid group name: $groupName")
                    }
                }
                updateHomeMovieMap()

            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error loading more movies for $groupName", e)
            }
        }
    }
}