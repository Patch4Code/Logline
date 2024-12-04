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
import com.patch4code.loglinemovieapp.features.person_details.domain.model.PersonDetailsSortOption
import com.patch4code.loglinemovieapp.features.person_details.domain.model.PersonMovieCredits
import kotlinx.coroutines.launch

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * PersonDetailsViewModel - ViewModel responsible for managing the data related to a person's
 * details and movie credits from TMDB api.
 *
 * @author Patch4Code
 */
class PersonDetailsViewModel: ViewModel() {

    private val tmdbApiService: TmdbApiService by lazy {
        RetrofitHelper.getInstance(TmdbCredentials.BASE_URL).create(TmdbApiService::class.java)
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _hasLoadError = MutableLiveData<Boolean>()
    val hasLoadError: LiveData<Boolean> get() = _hasLoadError


    private val _personDetails = MutableLiveData<PersonDetails>()
    val personDetails: LiveData<PersonDetails> get() = _personDetails

    private val _personMovieCredits = MutableLiveData<PersonMovieCredits>()

    private val _personCreditsMap = MutableLiveData<Map<String, List<Movie>>>()
    val personCreditsMap: LiveData<Map<String, List<Movie>>> get() = _personCreditsMap


    // Loads the details of the person with the given id from TMDB api
    fun loadPersonDetails(personId: Int){
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _hasLoadError.value = false

                val personDetailsResponse = tmdbApiService.getPersonDetails(personId = personId)
                if(personDetailsResponse.isSuccessful){
                    _personDetails.value = personDetailsResponse.body()
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _hasLoadError.value = true
                Log.e("PersonDetailsViewModel", "Error getting person details", e)
            }
        }
    }

    // Loads the movie credits of the person with the given id from TMDB api
    fun loadPersonMovieCredits(personId: Int, mainDepartment: String, sortOption: PersonDetailsSortOption){
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
                _personCreditsMap.value = createSortedPersonCreditsMap(mainDepartment, sortOption)
                _isLoading.value = false
            }
        }
    }

    //formats data for displaying by creating a map with department name and associated list of films
    private fun createSortedPersonCreditsMap(mainDepartment: String, sortOption: PersonDetailsSortOption): Map<String, List<Movie>> {

        // Initialize a temp mutable map to store department names and associated movie lists
        val tempPersonCreditsMap: MutableMap<String, List<Movie>> = mutableMapOf()

        // Determine the list of movies for the main department
        // TODO: Possibly string resources would also have to be used here (“Acting”)
        //  if the data is obtained from TMDB in different languages in the future
        val mainDepartmentMovies = if (mainDepartment == "Acting") {
            createCastMoviesList(sortOption)
        } else {
            createCrewMoviesList(mainDepartment, sortOption)
        }
        // Add the main department and its associated movie list to the map
        tempPersonCreditsMap["$mainDepartment (${mainDepartmentMovies.size} movies)"] = mainDepartmentMovies

        // If the main department is not "Acting", add this department and its associated movie list to the map
        if (mainDepartment != "Acting") {
            val castMovies = createCastMoviesList(sortOption)
            tempPersonCreditsMap["Acting (${castMovies.size} movies)"] = castMovies
        }

        // Iterate through unique crew departments and add these department and their associated
        // movie list to the map (main department excluded here because this was handled above)
        getUniqueDepartments().forEach { department ->
            if (department != mainDepartment) {
                val crewMovies = createCrewMoviesList(department, sortOption)
                tempPersonCreditsMap["$department (${crewMovies.size} movies)"] = crewMovies
            }
        }
        // Return the map containing department names and associated movie lists
       return tempPersonCreditsMap
    }

    // Creates a list of cast movies sorted by popularity
    private fun createCastMoviesList(sortOption: PersonDetailsSortOption): List<Movie> {
        return _personMovieCredits.value?.cast
            ?.sortedWith(
                when (sortOption) {
                    PersonDetailsSortOption.ByPopularityDesc -> compareByDescending { it.popularity }
                    PersonDetailsSortOption.ByPopularityAsc -> compareBy { it.popularity }
                    PersonDetailsSortOption.ByTitleAsc -> compareBy { it.title }
                    PersonDetailsSortOption.ByTitleDesc -> compareByDescending { it.title }
                    PersonDetailsSortOption.ByReleaseDateDesc -> compareByDescending { it.releaseDate }
                    PersonDetailsSortOption.ByReleaseDateAsc -> compareBy { it.releaseDate }
                }
            )
            ?.map {
                Movie(
                    title = it.title ?: "N/A",
                    id = it.id,
                    releaseDate = it.releaseDate ?: "N/A",
                    posterUrl = it.posterUrl ?: "N/A"
                )
            } ?: emptyList()
    }

    // Returns list of unique departments from the crew list
    private fun getUniqueDepartments(): List<String> {
        return _personMovieCredits.value?.crew
            ?.map { it.department }
            ?.distinct()
            ?: emptyList()
    }

    // Creates a list of crew movies sorted by popularity
    private fun createCrewMoviesList(department: String, sortOption: PersonDetailsSortOption): List<Movie>{
        return _personMovieCredits.value?.crew
            ?.filter {it.department == department}
            ?.distinctBy { it.id }
            ?.sortedWith(
                when (sortOption) {
                    PersonDetailsSortOption.ByPopularityDesc -> compareByDescending { it.popularity }
                    PersonDetailsSortOption.ByPopularityAsc -> compareBy { it.popularity }
                    PersonDetailsSortOption.ByTitleAsc -> compareBy { it.title }
                    PersonDetailsSortOption.ByTitleDesc -> compareByDescending { it.title }
                    PersonDetailsSortOption.ByReleaseDateDesc -> compareByDescending { it.releaseDate }
                    PersonDetailsSortOption.ByReleaseDateAsc -> compareBy { it.releaseDate }
                }
            )
            ?.map{
                Movie(
                    title = it.title ?: "N/A",
                    id = it.id,
                    releaseDate = it.releaseDate ?: "N/A",
                    posterUrl = it.posterUrl ?: "N/A"
                )
            } ?: emptyList()
    }

    fun updateSortingForPersonMovieCredits(mainDepartment: String, sortOption: PersonDetailsSortOption) {
        _personCreditsMap.value = createSortedPersonCreditsMap(mainDepartment, sortOption)
    }
}