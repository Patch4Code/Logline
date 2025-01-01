package com.patch4code.loglinemovieapp.features.search.presentation.screen_search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patch4code.loglinemovieapp.api.RetrofitHelper
import com.patch4code.loglinemovieapp.api.TmdbApiService
import com.patch4code.loglinemovieapp.features.core.presentation.utils.TmdbCredentials
import com.patch4code.loglinemovieapp.features.movie.domain.model.Provider
import kotlinx.coroutines.launch

class ProviderForCountryViewModel: ViewModel(){

    private val tmdbApiService: TmdbApiService by lazy {
        RetrofitHelper.getInstance(TmdbCredentials.BASE_URL).create(TmdbApiService::class.java)
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _hasLoadError = MutableLiveData<Boolean>()
    val hasLoadError: LiveData<Boolean> get() = _hasLoadError

    private val _movieProvidersForCountry = MutableLiveData<List<Provider>?>()
    val movieProvidersForCountry: MutableLiveData<List<Provider>?> get() = _movieProvidersForCountry

    private val _lastWatchRegion = MutableLiveData<String?>()

    fun loadMovieProviders(watchRegion: String?){

        if(!_movieProvidersForCountry.value.isNullOrEmpty()
            && (watchRegion == _lastWatchRegion.value))  return


        _lastWatchRegion.value = watchRegion

        viewModelScope.launch {
            try {
                _isLoading.value = true
                _hasLoadError.value = false

                val searchResponse = tmdbApiService.getMovieProvidersForCountry(watchRegion = watchRegion)
                if(searchResponse.isSuccessful){
                    _movieProvidersForCountry.value = searchResponse.body()?.movieProviders
                }
                _isLoading.value = false
            }catch (e: Exception){
                _isLoading.value = false
                _hasLoadError.value = true
                Log.e("DiscoverViewModel", "Error loading movieProvidersForCountry", e)
            }
        }
    }
}