package com.moritz.movieappuitest.features.search.presentation.screen_search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moritz.movieappuitest.api.RetrofitHelper
import com.moritz.movieappuitest.api.TmdbApiService
import com.moritz.movieappuitest.features.core.domain.model.Movie
import com.moritz.movieappuitest.features.core.presentation.utils.TmdbCredentials
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel(){

    private val tmdbApiService: TmdbApiService by lazy {
        RetrofitHelper.getInstance(TmdbCredentials.BASE_URL).create(TmdbApiService::class.java)
    }

    private val _searchedMovies = MutableLiveData<List<Movie>>()
    val searchedMovies: LiveData<List<Movie>>
        get() = _searchedMovies

    private var highestLoadedPage = 1
    private var pageAmount = 1
    private var currentSearchQuery = ""

    fun searchMovie(searchQuery: String){
        currentSearchQuery = searchQuery

        viewModelScope.launch {
            try {
                val searchResponse = tmdbApiService.searchMovie(searchQuery = searchQuery)
                if(searchResponse.isSuccessful){
                    pageAmount = searchResponse.body()?.totalPages!!
                    _searchedMovies.value = searchResponse.body()?.results
                }
            } catch (e: Exception) {
                Log.e("SearchViewModel", "Error searching movies", e)
            }
            finally {
                highestLoadedPage = 1
            }
        }
    }

    fun loadMoreMovies(){
        if(highestLoadedPage < pageAmount){
            highestLoadedPage++
            //Log.e("Page-Number index", "CurrentPage: $highestLoadedPage, MaxPage: $pageAmount")
            val currentMovies = _searchedMovies.value?.toMutableList() ?: mutableListOf()

            viewModelScope.launch {
                try {
                    val loadMoreResponse = tmdbApiService.searchMovie(searchQuery = currentSearchQuery, page = highestLoadedPage)

                    if(loadMoreResponse.isSuccessful){
                        val newLoadedMovies = loadMoreResponse.body()?.results

                        if (newLoadedMovies != null) {
                            currentMovies.addAll(newLoadedMovies)
                        }
                        _searchedMovies.value = currentMovies
                    }
                } catch (e: Exception) {
                    Log.e("SearchViewModel", "Error loading more movies", e)
                }
            }
        }
    }
}