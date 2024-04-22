package com.patch4code.loglinemovieapp.features.person_details.presentation.screen_person

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patch4code.loglinemovieapp.api.RetrofitHelper
import com.patch4code.loglinemovieapp.api.TmdbApiService
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.presentation.utils.TmdbCredentials
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

    private val _personCreditsMap = MutableLiveData<Map<String, List<Movie>>>()
    val personCreditsMap: LiveData<Map<String, List<Movie>>>
        get() = _personCreditsMap

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun loadPersonDetails(personId: Int){
        viewModelScope.launch {
            try {
                _isLoading.value = true

                val personDetailsResponse = tmdbApiService.getPersonDetails(personId = personId)
                if(personDetailsResponse.isSuccessful){
                    _personDetails.value = personDetailsResponse.body()
                }
            } catch (e: Exception) {
                Log.e("PersonDetailsViewModel", "Error getting person details", e)
            }
        }
    }

    fun loadPersonMovieCredits(personId: Int, mainDepartment: String){
        viewModelScope.launch {
            try {
                _isLoading.value = true

                val personMovieCreditsResponse = tmdbApiService.getPersonMovieCredits(personId)
                if(personMovieCreditsResponse.isSuccessful){
                    _personMovieCredits.value = personMovieCreditsResponse.body()
                }
            } catch (e: Exception) {
                Log.e("PersonDetailsViewModel", "Error getting person movie credits", e)
            }finally{
                _personCreditsMap.value = createPersonCreditsMap(mainDepartment)
                _isLoading.value = false
            }
        }
    }

    //format data for displaying -->
    private fun createPersonCreditsMap(mainDepartment: String): Map<String, List<Movie>> {

        val tempPersonCreditsMap: MutableMap<String, List<Movie>> = mutableMapOf()

        val mainDepartmentMovies = if (mainDepartment == "Acting") {
            createCastMoviesList()
        } else {
            createCrewMoviesList(mainDepartment)
        }
        tempPersonCreditsMap["$mainDepartment (${mainDepartmentMovies.size} movies)"] = mainDepartmentMovies

        if (mainDepartment != "Acting") {
            val castMovies = createCastMoviesList()
            tempPersonCreditsMap["Acting (${castMovies.size} movies)"] = castMovies
        }

        getUniqueDepartments().forEach { department ->
            if (department != mainDepartment) {
                val crewMovies = createCrewMoviesList(department)
                tempPersonCreditsMap["$department (${crewMovies.size} movies)"] = crewMovies
            }
        }
       return tempPersonCreditsMap
    }

    private fun createCastMoviesList(): List<Movie> {
        return _personMovieCredits.value?.cast
            ?.sortedByDescending { it.popularity}
            ?.map {
                Movie(
                    title = it.title ?: "N/A",
                    id = it.id,
                    releaseDate = it.releaseDate ?: "N/A",
                    posterUrl = it.posterUrl ?: "N/A"
                )
            } ?: emptyList()
    }

    private fun getUniqueDepartments(): List<String> {
        return _personMovieCredits.value?.crew
            ?.map { it.department }
            ?.distinct()
            ?: emptyList()
    }

    private fun createCrewMoviesList(department: String): List<Movie>{
        return _personMovieCredits.value?.crew
            ?.filter {it.department == department}
            ?.distinctBy { it.id }
            ?.sortedByDescending { it.popularity }
            ?.map{
                Movie(
                    title = it.title ?: "N/A",
                    id = it.id,
                    releaseDate = it.releaseDate ?: "N/A",
                    posterUrl = it.posterUrl ?: "N/A"
                )
            } ?: emptyList()
    }
}