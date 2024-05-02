package com.patch4code.loglinemovieapp.features.social.presentation.screen_public_profile_page

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.reflect.TypeToken
import com.parse.ParseObject
import com.parse.ParseQuery
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.core.presentation.utils.JSONHelper
import com.patch4code.loglinemovieapp.features.list.domain.model.MovieList
import com.patch4code.loglinemovieapp.features.social.domain.model.PublicList
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import java.util.concurrent.CountDownLatch
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * PublicProfileListsViewModel - ViewModel responsible for fetching and managing public user profile lists data.
 *
 * @author Patch4Code
 */
class PublicProfileListsViewModel: ViewModel() {

    private val _publicProfileLists = MutableLiveData<List<PublicList>>()
    val publicProfileLists: LiveData<List<PublicList>> get() = _publicProfileLists

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    // Fetches public lists from a user based on given userId
    fun getPublicProfileLists(userId: String?){
        viewModelScope.launch {
            try {
                _isLoading.value = true

                // Kotlin suspendCoroutine waits for the completion of the background operation
                val publicProfileListsAsParseObjects = suspendCoroutine<List<ParseObject>> { continuation ->
                    val profileListsQuery = ParseQuery<ParseObject>("MovieList")
                    profileListsQuery.whereEqualTo("user", ParseObject.createWithoutData("_User", userId))
                    // Execute query in background
                    profileListsQuery.findInBackground { publicListsAsParseObjects, e ->
                        if(e != null){
                            Log.e("PublicProfileListsViewModel", "Error: ", e)
                            continuation.resumeWithException(e)
                        }else{
                            continuation.resume(publicListsAsParseObjects)
                        }
                    }
                }

                if (publicProfileListsAsParseObjects.isEmpty()){
                    _publicProfileLists.value = emptyList()
                }else{
                    val publicProfileMovieLists = buildPublicMovieLists(publicProfileListsAsParseObjects)
                    _publicProfileLists.value = publicProfileMovieLists.sortedByDescending { it.publishedAt }
                }
            }catch (e: Exception){
                Log.e("PublicProfileListsViewModel", "Catch Error: ", e)
            }finally {
                _isLoading.value = false
            }
        }
    }

    // Creates List<PublicList> from given List<ParseObject>
    private suspend fun buildPublicMovieLists(publicProfileListsAsParseObjects: List<ParseObject>): List<PublicList>{
        return suspendCoroutine {continuation ->
            val publicProfileMovieLists = mutableListOf<PublicList>()
            val countDownLatch = CountDownLatch(publicProfileListsAsParseObjects.size)

            publicProfileListsAsParseObjects.forEach{ list->
                val objectId = list.objectId
                val user = list.getParseUser("user")
                val userId = user?.objectId ?: ""
                val listName = list.getString("name") ?: "List Name Error"
                val moviesJson = list.getString("moviesString")
                val type: Type = object : TypeToken<List<Movie>>() {}.type
                val movies: List<Movie> = JSONHelper.fromJsonWithType(moviesJson, type)
                val isRanked = list.getBoolean("isRanked")
                val publishedAt = list.createdAt

                var authorName = "Anonymous"
                var avatarPath = ""

                val profileQuery = ParseQuery.getQuery<ParseObject>("UserProfile")
                profileQuery.whereEqualTo("user", user)
                profileQuery.findInBackground { profiles, e ->
                    if (e != null) {
                        Log.e("PublicProfileListsViewModel", "Error loading user info: ", e)
                    } else {
                        val profile = profiles.firstOrNull()
                        if (profile != null) {
                            avatarPath = profile.getParseFile("profileImage")?.url ?: ""
                            authorName = profile.getString("userName") ?: "Anonymous"
                        }
                    }
                    val publicList = PublicList(
                        objectId = objectId,
                        userId = userId,
                        publishedAt = publishedAt,
                        authorName = authorName,
                        avatarPath = avatarPath,
                        movieList = MovieList(
                            name = listName,
                            movies = movies,
                            isRanked = isRanked
                        ),
                        isProfilePublic = true
                    )

                    publicProfileMovieLists.add(publicList)

                    countDownLatch.countDown()
                    if (countDownLatch.count == 0L) {
                        continuation.resume(publicProfileMovieLists)
                    }
                }
            }
        }
    }
}