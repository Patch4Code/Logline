package com.patch4code.loglinemovieapp.features.person_details.presentation.screen_person

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patch4code.loglinemovieapp.api.RetrofitHelper
import com.patch4code.loglinemovieapp.api.TmdbApiService
import com.patch4code.loglinemovieapp.features.core.presentation.utils.TmdbCredentials
import com.patch4code.loglinemovieapp.features.person_details.domain.model.PersonDetails
import kotlinx.coroutines.launch

class PersonDetailsViewModel: ViewModel() {

    private val tmdbApiService: TmdbApiService by lazy {
        RetrofitHelper.getInstance(TmdbCredentials.BASE_URL).create(TmdbApiService::class.java)
    }

    private val _personDetails = MutableLiveData<PersonDetails>()
    val personDetails: LiveData<PersonDetails> get() = _personDetails

    fun loadPersonDetails(personId: Int){
        viewModelScope.launch {
            try {
                val personDetailsResponse = tmdbApiService.getPersonDetails(personId = personId)
                Log.e("PersonDetailsViewModel", "before isSuccessful")
                if(personDetailsResponse.isSuccessful){
                    Log.e("PersonDetailsViewModel", "isSuccessful")
                    _personDetails.value = personDetailsResponse.body()
                }
            } catch (e: Exception) {
                Log.e("PersonDetailsViewModel", "Error getting person details", e)
            }
        }
    }

}