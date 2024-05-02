package com.patch4code.loglinemovieapp.features.social.presentation.screen_public_lists

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
 * PublicListsTableViewModel - ViewModel responsible for fetching and managing public lists data.
 *
 * @author Patch4Code
 */
class PublicListsTableViewModel: ViewModel() {

    private val _publicLists = MutableLiveData<List<PublicList>>()
    val publicLists: LiveData<List<PublicList>> get() = _publicLists

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    // Fetches public lists from the database.
    fun getPublicLists(){
        viewModelScope.launch {
            try {
                _isLoading.value = true

                // Kotlin suspendCoroutine waits for the completion of the background operation
                val publicListsAsParseObjects = suspendCoroutine<List<ParseObject>> { continuation ->
                    val listsQuery = ParseQuery<ParseObject>("MovieList")
                    // Execute query in background
                    listsQuery.findInBackground { publicListsAsParseObjects, e ->
                        if(e != null){
                            Log.e("PublicListsViewModel", "Error: ", e)
                            continuation.resumeWithException(e)
                        }else{
                            continuation.resume(publicListsAsParseObjects)
                        }
                    }
                }

                val publicMovieLists = buildPublicMovieLists(publicListsAsParseObjects)
                _publicLists.value = publicMovieLists.sortedByDescending { it.publishedAt }
            }catch (e: Exception){
                Log.e("PublicListsViewModel", "Catch Error: ", e)
            }finally {
                _isLoading.value = false
            }
        }
    }

    // Creates List<PublicList> from given List<ParseObject>
    private suspend fun buildPublicMovieLists(publicListsAsParseObjects: List<ParseObject>): List<PublicList>{
        return suspendCoroutine {continuation ->
            val publicMovieLists = mutableListOf<PublicList>()
            val countDownLatch = CountDownLatch(publicListsAsParseObjects.size)

            publicListsAsParseObjects.forEach{ list->
                val objectId = list.objectId
                val user = list.getParseUser("user")
                val userId = user?.objectId ?: ""
                val listName = list.getString("name") ?: "List Name Error"
                val moviesJson = list.getString("moviesString")
                val type: Type = object : TypeToken<List<Movie>>() {}.type
                val movies: List<Movie> = JSONHelper.fromJsonWithType(moviesJson, type)
                val isRanked = list.getBoolean("isRanked")
                val publishedAt = list.createdAt

                var isProfilePublic = false
                var authorName = "Anonymous"
                var avatarPath = ""

                val profileQuery = ParseQuery.getQuery<ParseObject>("UserProfile")
                profileQuery.whereEqualTo("isPublic", true)
                profileQuery.whereEqualTo("user", user)
                profileQuery.findInBackground { profiles, e ->
                    if (e != null) {
                        Log.e("PublicListsViewModel", "Error loading user info: ", e)
                    } else {
                        val profile = profiles.firstOrNull()
                        if (profile != null) {
                            isProfilePublic = profile.getBoolean("isPublic")
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
                        isProfilePublic = isProfilePublic
                    )

                    publicMovieLists.add(publicList)

                    countDownLatch.countDown()
                    if (countDownLatch.count == 0L) {
                        continuation.resume(publicMovieLists)
                    }
                }
            }
        }
    }
}