package com.patch4code.loglinemovieapp.features.search.presentation.screen_search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.patch4code.loglinemovieapp.api.RetrofitHelper
import com.patch4code.loglinemovieapp.api.TmdbApiService
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.presentation.utils.TmdbCredentials
import com.patch4code.loglinemovieapp.features.search.domain.model.SearchHistoryItem
import com.patch4code.loglinemovieapp.room_database.SearchHistoryDao
import kotlinx.coroutines.launch

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * SearchViewModel - ViewModel responsible for handling movie search functionality for the SearchView.
 * This ViewModel communicates with TMDB API to search for movies based on given searchQuery.
 *
 * @author Patch4Code
 */
class SearchViewModel(private val dao: SearchHistoryDao): ViewModel(){


    private val tmdbApiService: TmdbApiService by lazy {
        RetrofitHelper.getInstance(TmdbCredentials.BASE_URL).create(TmdbApiService::class.java)
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _hasLoadError = MutableLiveData<Boolean>()
    val hasLoadError: LiveData<Boolean> get() = _hasLoadError


    private val _searchedMovies = MutableLiveData<List<Movie>?>()
    val searchedMovies: MutableLiveData<List<Movie>?> get() = _searchedMovies

    private var highestLoadedPage = 1
    private var pageAmount = 1
    private var currentSearchQuery = ""

    private val _searchHistory = MutableLiveData<List<SearchHistoryItem>>()
    val searchHistory: MutableLiveData<List<SearchHistoryItem>> get() = _searchHistory


    // Initiates search for movies based on query and saves the result in _searchedMovies live data
    fun searchMovie(searchQuery: String){
        currentSearchQuery = searchQuery

        viewModelScope.launch {
            try {
                _isLoading.value = true
                _hasLoadError.value = false

                val searchResponse = tmdbApiService.searchMovie(searchQuery = searchQuery)
                if(searchResponse.isSuccessful){
                    pageAmount = searchResponse.body()?.totalPages!!
                    _searchedMovies.value = searchResponse.body()?.results
                }
                _isLoading.value = false
            } catch (e: Exception) {
                _isLoading.value = false
                _hasLoadError.value = true
                Log.e("SearchViewModel", "Error searching movies", e)
            }
            finally {
                highestLoadedPage = 1
                addSearchStringToSearchHistory(searchQuery)
            }
        }
    }

    // loads the next page of movies and adds the newly found movies to the list of already loaded movies
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

    fun clearSearchedMovies(){
        _searchedMovies.value = null
    }

    fun loadSearchHistory(){
        viewModelScope.launch {
            _searchHistory.value = dao.getSearchHistory()
        }
    }
    private fun addSearchStringToSearchHistory(searchString: String){
        viewModelScope.launch {
            dao.addSearchString(searchString.trimEnd())
            loadSearchHistory()
        }
    }
    fun deleteItemFromSearchHistory(searchHistoryItem: SearchHistoryItem){
        viewModelScope.launch {
            dao.deleteSearchHistoryItem(searchHistoryItem)
            loadSearchHistory()
        }
    }
    fun clearSearchHistory(){
        viewModelScope.launch {
            dao.clearSearchHistory()
            loadSearchHistory()
        }
    }
}

// Factory-class for creating SearchViewModel instances to manage access to the database
class SearchViewModelFactory(private val dao: SearchHistoryDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}