package com.patch4code.loglinemovieapp.features.search.presentation.screen_search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.patch4code.loglinemovieapp.api.RetrofitHelper
import com.patch4code.loglinemovieapp.api.TmdbApiService
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.presentation.utils.TmdbCredentials

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

}