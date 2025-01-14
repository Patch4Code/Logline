package com.patch4code.loglinemovieapp.room_database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie
import com.patch4code.loglinemovieapp.features.profile.domain.model.UserProfile
import com.patch4code.loglinemovieapp.features.profile.domain.model.UserProfileWithFavouriteMovies
import com.patch4code.loglinemovieapp.room_database.utils.Queries

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * UserProfileDao - Data Access Object (DAO) interface for
 * performing database operations related to UserProfile entities.
 *
 * @author Patch4Code
 */
@Dao
interface UserProfileDao {

    @Upsert
    suspend fun upsertUserProfile(userProfile: UserProfile)

    @Upsert
    suspend fun upsertMovie(movie: Movie)

    @Query("SELECT * From UserProfile WHERE id = 1 LIMIT 1")
    suspend fun getUserProfile(): UserProfile?

    @Transaction
    suspend fun getFavouriteMovies(): List<Movie?> {

        val userProfile = getUserProfile()
        val favMovieIds = listOf(
            userProfile?.favouriteMovieId1,
            userProfile?.favouriteMovieId2,
            userProfile?.favouriteMovieId3,
            userProfile?.favouriteMovieId4
        )
        val favouriteMovies = mutableListOf<Movie?>()

        for(id in favMovieIds){
            if (id != null){
                val movie = getMovieById(id)
                favouriteMovies.add(movie)
            } else{
                favouriteMovies.add(null)
            }
        }
        return favouriteMovies
    }

    @Query("SELECT * FROM Movie WHERE id = :movieId")
    suspend fun getMovieById(movieId: Int): Movie?

    @Transaction
    suspend fun getUserProfileWithFavouriteMovies(): UserProfileWithFavouriteMovies? {
        val userProfile = getUserProfile() ?: return null
        val favouriteMovies = getFavouriteMovies()

        return UserProfileWithFavouriteMovies(
            userProfile = userProfile,
            favouriteMovies = favouriteMovies
        )
    }

    @Transaction
    suspend fun updateProfileName(profileName: String){
        val userProfileUpdate = getUserProfile()
        if (userProfileUpdate != null) {
            userProfileUpdate.username = profileName
            upsertUserProfile(userProfileUpdate)
        }
    }

    @Transaction
    suspend fun updateBio(bioText: String){
        val userProfileUpdate = getUserProfile()
        if (userProfileUpdate != null){
            userProfileUpdate.bioText = bioText
            upsertUserProfile(userProfileUpdate)
        }
    }

    @Transaction
    suspend fun setProfileImagePath(path: String){
        val userProfileUpdate = getUserProfile()
        if (userProfileUpdate != null) {
            userProfileUpdate.profileImagePath = path
            upsertUserProfile(userProfileUpdate)
        }
    }

    @Transaction
    suspend fun setBannerImagePath(path: String){
        val userProfileUpdate = getUserProfile()
        if (userProfileUpdate != null) {
            userProfileUpdate.bannerImagePath = path
            upsertUserProfile(userProfileUpdate)
        }
    }

    @Transaction
    suspend fun setFavMovieAtIndex(index: Int, movie: Movie?){

        val userProfileUpdate = getUserProfile()

        if (userProfileUpdate != null){

            //update movieId at index
            when (index) {
                0 -> userProfileUpdate.favouriteMovieId1 = movie?.id
                1 -> userProfileUpdate.favouriteMovieId2 = movie?.id
                2 -> userProfileUpdate.favouriteMovieId3 = movie?.id
                3 -> userProfileUpdate.favouriteMovieId4 = movie?.id
            }

            //add new movie
            if(movie != null){
                upsertMovie(movie)
            }

            //save changes to UserProfile
            upsertUserProfile(userProfileUpdate)
        }
    }

    @Transaction
    suspend fun deleteFavMovieAtIndex(index: Int){
        val userProfileUpdate = getUserProfile()

        if (userProfileUpdate != null) {

            val oldMovieId = when (index) {
                0 -> userProfileUpdate.favouriteMovieId1
                1 -> userProfileUpdate.favouriteMovieId2
                2 -> userProfileUpdate.favouriteMovieId3
                3 -> userProfileUpdate.favouriteMovieId4
                else -> throw IllegalArgumentException("Invalid index: $index")
            }

            when (index) {
                0 -> userProfileUpdate.favouriteMovieId1 = null
                1 -> userProfileUpdate.favouriteMovieId2 = null
                2 -> userProfileUpdate.favouriteMovieId3 = null
                3 -> userProfileUpdate.favouriteMovieId4 = null
            }

            //save changes to UserProfile
            upsertUserProfile(userProfileUpdate)

            //delete movie if there are no more references
            if (oldMovieId != null && countMovieReferences(oldMovieId) < 1){
                deleteMovieById(oldMovieId)
            }
        }
    }

    @Query(Queries.COUNT_MOVIE_REFERENCES)
    suspend fun countMovieReferences(movieId: Int?): Int

    @Query("DELETE FROM Movie WHERE id = :movieId")
    suspend fun deleteMovieById(movieId: Int)
}