package com.patch4code.loglinemovieapp.features.core.presentation

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

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * GeneralMovieSearchViewModel - ViewModel responsible for handling movie search functionality
 * This ViewModel communicates with TMDB API to search for movies based on given searchQuery
 *
 * @author Patch4Code
 */
class GeneralMovieSearchViewModel: ViewModel() {

    private val tmdbApiService: TmdbApiService by lazy {
        RetrofitHelper.getInstance(TmdbCredentials.BASE_URL).create(TmdbApiService::class.java)
    }

    private val _hasLoadError = MutableLiveData<Boolean>()
    val hasLoadError: LiveData<Boolean> get() = _hasLoadError

    private val _searchedMovies = MutableLiveData<List<Movie>>()
    val searchedMovies: LiveData<List<Movie>> get() = _searchedMovies

    // Initiates search for movies based on query and saves the result in _searchedMovies live data
    fun searchMovie(searchQuery: String){
        viewModelScope.launch {
            try {
                _hasLoadError.value = false

                val searchResponse = tmdbApiService.searchMovie(searchQuery = searchQuery)
                if(searchResponse.isSuccessful){
                    _searchedMovies.value = searchResponse.body()?.results
                }
            } catch (e: Exception) {
                _hasLoadError.value = true
                Log.e("SearchViewModel", "Error searching movies", e)
            }
        }
    }
}