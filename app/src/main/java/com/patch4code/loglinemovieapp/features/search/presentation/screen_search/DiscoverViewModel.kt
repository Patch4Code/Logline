package com.patch4code.loglinemovieapp.features.search.presentation.screen_search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patch4code.loglinemovieapp.api.RetrofitHelper
import com.patch4code.loglinemovieapp.api.TmdbApiService
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.presentation.utils.TmdbCredentials
import com.patch4code.loglinemovieapp.features.search.domain.model.DiscoverOptions
import kotlinx.coroutines.launch

class DiscoverViewModel(): ViewModel(){

    private val tmdbApiService: TmdbApiService by lazy {
        RetrofitHelper.getInstance(TmdbCredentials.BASE_URL).create(TmdbApiService::class.java)
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _hasLoadError = MutableLiveData<Boolean>()
    val hasLoadError: LiveData<Boolean> get() = _hasLoadError

    private val _discoveredMovies = MutableLiveData<List<Movie>?>()
    val discoveredMovies: MutableLiveData<List<Movie>?> get() = _discoveredMovies

    private var highestLoadedPage = 1
    private var pageAmount = 1

    fun loadDiscoveredMovies(discoverOptions: DiscoverOptions){
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _hasLoadError.value = false

                val searchResponse = tmdbApiService.discoverMovies(
                    sortBy = discoverOptions.sortBy,
                    genres = discoverOptions.getGenresAsString(","),
                    primaryReleaseYear = discoverOptions.primaryReleaseYear,
                    primaryReleaseDateGte = discoverOptions.primaryReleaseDateGte,
                    primaryReleaseDateLte = discoverOptions.primaryReleaseDateLte,
                    originCountry = discoverOptions.originCountry,
                    originalLanguage = discoverOptions.originalLanguage,
                    watchRegion = discoverOptions.watchRegion,
                    watchProviders = discoverOptions.watchProviders,
                    runtimeGte = discoverOptions.runtimeGte,
                    runtimeLte = discoverOptions.runtimeLte,
                    voteAverageGte = discoverOptions.voteAverageGte,
                    voteAverageLte = discoverOptions.voteAverageLte,
                    voteCountGte = discoverOptions.voteCountGte,
                    people = discoverOptions.people,
                    companies = discoverOptions.companies
                )
                if(searchResponse.isSuccessful){
                    pageAmount = searchResponse.body()?.totalPages!!
                    _discoveredMovies.value = searchResponse.body()?.results
                }

                _isLoading.value = false
            }catch (e: Exception){
                _isLoading.value = false
                _hasLoadError.value = true
                Log.e("DiscoverViewModel", "Error loading discovered movies", e)

            }finally {
                highestLoadedPage = 1
            }
        }
    }

    fun loadMoreDiscoveredMovies(discoverOptions: DiscoverOptions){
        if(highestLoadedPage < pageAmount){
            highestLoadedPage++
            val currentDiscoveredMovies = _discoveredMovies.value?.toMutableList() ?: mutableListOf()

            viewModelScope.launch {
                try {
                    val loadMoreResponse = tmdbApiService.discoverMovies(
                        page = highestLoadedPage,
                        sortBy = discoverOptions.sortBy,
                        genres = discoverOptions.getGenresAsString(","),
                        primaryReleaseYear = discoverOptions.primaryReleaseYear,
                        primaryReleaseDateGte = discoverOptions.primaryReleaseDateGte,
                        primaryReleaseDateLte = discoverOptions.primaryReleaseDateLte,
                        originCountry = discoverOptions.originCountry,
                        originalLanguage = discoverOptions.originalLanguage,
                        watchRegion = discoverOptions.watchRegion,
                        watchProviders = discoverOptions.watchProviders,
                        runtimeGte = discoverOptions.runtimeGte,
                        runtimeLte = discoverOptions.runtimeLte,
                        voteAverageGte = discoverOptions.voteAverageGte,
                        voteAverageLte = discoverOptions.voteAverageLte,
                        voteCountGte = discoverOptions.voteCountGte,
                        people = discoverOptions.people,
                        companies = discoverOptions.companies
                    )
                    if(loadMoreResponse.isSuccessful){
                        val newLoadedDiscoveredMovies = loadMoreResponse.body()?.results

                        if (newLoadedDiscoveredMovies != null){
                            currentDiscoveredMovies.addAll(newLoadedDiscoveredMovies)
                        }
                        _discoveredMovies.value = currentDiscoveredMovies
                    }
                }catch (e: Exception){
                    Log.e("DiscoverViewModel", "Error loading more discovered movies", e)
                }
            }
        }
    }

    fun clearDiscoveredMovies(){
        _isLoading.value = false
        _hasLoadError.value = false
        _discoveredMovies.value = null
    }
}