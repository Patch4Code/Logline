package com.patch4code.loglinemovieapp.features.person_details.presentation.screen_person

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patch4code.loglinemovieapp.api.RetrofitHelper
import com.patch4code.loglinemovieapp.api.TmdbApiService
import com.patch4code.loglinemovieapp.features.core.presentation.utils.TmdbCredentials
import com.patch4code.loglinemovieapp.features.person_details.domain.model.MovieAsCastMember
import com.patch4code.loglinemovieapp.features.person_details.domain.model.MovieAsCrewMember
import com.patch4code.loglinemovieapp.features.person_details.domain.model.PersonDetails
import com.patch4code.loglinemovieapp.features.person_details.domain.model.PersonMovieCredits
import kotlinx.coroutines.launch

class PersonDetailsViewModel: ViewModel() {

    private val tmdbApiService: TmdbApiService by lazy {
        RetrofitHelper.getInstance(TmdbCredentials.BASE_URL).create(TmdbApiService::class.java)
    }

    private val _personDetails = MutableLiveData<PersonDetails>()
    val personDetails: LiveData<PersonDetails> get() = _personDetails


    private val _personMovieCredits = MutableLiveData<PersonMovieCredits>()

    private val _personCastCreditsMap = MutableLiveData<Pair<String, List<MovieAsCastMember>>>()
    val personCastCreditsMap: LiveData<Pair<String, List<MovieAsCastMember>>>
        get() = _personCastCreditsMap

    private val _personCrewCreditsMap = MutableLiveData<Map<String, List<MovieAsCrewMember>>>()
    val personCrewCreditsMap: LiveData<Map<String, List<MovieAsCrewMember>>>
        get() = _personCrewCreditsMap


    fun loadPersonDetails(personId: Int){
        viewModelScope.launch {
            try {
                val personDetailsResponse = tmdbApiService.getPersonDetails(personId = personId)
                if(personDetailsResponse.isSuccessful){
                    _personDetails.value = personDetailsResponse.body()
                }
            } catch (e: Exception) {
                Log.e("PersonDetailsViewModel", "Error getting person details", e)
            }
        }
    }

    fun loadPersonMovieCredits(personId: Int){
        viewModelScope.launch {
            try {
                val personMovieCreditsResponse = tmdbApiService.getPersonMovieCredits(personId)
                if(personMovieCreditsResponse.isSuccessful){
                    _personMovieCredits.value = personMovieCreditsResponse.body()
                    //Log.e("PersonDetailsViewModel", "personMovieCreditsResponse: ${_personMovieCredits.value}}")
                }
            } catch (e: Exception) {
                Log.e("PersonDetailsViewModel", "Error getting person movie credits", e)
            }
            finally { //now separate data for displaying -->
                _personCastCreditsMap.value = _personMovieCredits.value?.let { Pair("Actor", it.cast) }
                // ... Crew ...
            }
        }
    }

}