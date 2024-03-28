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

    fun loadPersonMovieCredits(personId: Int, mainDepartment: String){
        viewModelScope.launch {
            try {
                val personMovieCreditsResponse = tmdbApiService.getPersonMovieCredits(personId)
                if(personMovieCreditsResponse.isSuccessful){
                    _personMovieCredits.value = personMovieCreditsResponse.body()
                }
            } catch (e: Exception) {
                Log.e("PersonDetailsViewModel", "Error getting person movie credits", e)
            }
            finally { //now separate data for displaying -->
                val tempPersonCreditsMap: MutableMap<String, List<Movie>> = mutableMapOf()

                //Main Department
                if (mainDepartment == "Acting"){
                    val mainDepCastMovies: List<Movie> = _personMovieCredits.value?.cast
                        ?.sortedByDescending { it.popularity}
                        ?.map {
                        Movie(
                            title = it.title ?: "N/A",
                            id = it.id,
                            releaseDate = it.releaseDate ?: "N/A",
                            posterUrl = it.posterUrl ?: "N/A"
                        )
                    } ?: emptyList()
                    tempPersonCreditsMap["Acting (${mainDepCastMovies.size} movies)"] = mainDepCastMovies

                    val uniqueDepartments: List<String> = _personMovieCredits.value?.crew
                        ?.map { it.department }
                        ?.distinct()
                        ?: emptyList()
                    for(department in uniqueDepartments){
                        val crewMovies: List<Movie> = _personMovieCredits.value?.crew
                            ?.filter {it.department == department}
                            ?.sortedByDescending { it.popularity }
                            ?.map{
                                Movie(
                                    title = it.title ?: "N/A",
                                    id = it.id,
                                    releaseDate = it.releaseDate ?: "N/A",
                                    posterUrl = it.posterUrl ?: "N/A"
                                )
                            } ?: emptyList()
                        tempPersonCreditsMap["$department (${crewMovies.size} movies)"] = crewMovies
                    }

                }else{
                    val mainDepCrewMovies: List<Movie> = _personMovieCredits.value?.crew
                        ?.filter {it.department == mainDepartment}
                        ?.distinctBy { it.id }
                        ?.sortedByDescending { it.popularity}
                        ?.map {
                            Movie(
                                title = it.title ?: "N/A",
                                id = it.id,
                                releaseDate = it.releaseDate ?: "N/A",
                                posterUrl = it.posterUrl ?: "N/A"
                            )
                        } ?: emptyList()
                    tempPersonCreditsMap["$mainDepartment (${mainDepCrewMovies.size} movies)"] = mainDepCrewMovies

                    val castMovies: List<Movie> = _personMovieCredits.value?.cast
                        ?.sortedByDescending { it.popularity}
                        ?.map {
                            Movie(
                                title = it.title ?: "N/A",
                                id = it.id,
                                releaseDate = it.releaseDate ?: "N/A",
                                posterUrl = it.posterUrl ?: "N/A"
                            )
                        } ?: emptyList()
                    tempPersonCreditsMap["Acting (${castMovies.size} movies)"] = castMovies

                    val uniqueDepartments: List<String> = _personMovieCredits.value?.crew
                        ?.filter {it.department != mainDepartment}
                        ?.map { it.department }
                        ?.distinct()
                        ?: emptyList()
                    for(department in uniqueDepartments){
                        val crewMovies: List<Movie> = _personMovieCredits.value?.crew
                            ?.filter {it.department == department}
                            ?.sortedByDescending { it.popularity }
                            ?.map{
                                Movie(
                                    title = it.title ?: "N/A",
                                    id = it.id,
                                    releaseDate = it.releaseDate ?: "N/A",
                                    posterUrl = it.posterUrl ?: "N/A"
                                )
                            } ?: emptyList()
                        tempPersonCreditsMap["$department (${crewMovies.size} movies)"] = crewMovies
                    }
                }

                _personCreditsMap.value = tempPersonCreditsMap
            }
        }
    }

}