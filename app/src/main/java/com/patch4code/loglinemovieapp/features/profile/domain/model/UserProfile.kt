package com.patch4code.loglinemovieapp.features.profile.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.patch4code.loglinemovieapp.features.core.domain.model.Movie

@Entity
data class UserProfile(
    val username: String = DEFAULT_USERNAME,
    val profileImagePath: String = DEFAULT_PROFILE_IMAGE_PATH,
    val bannerImagePath: String = DEFAULT_BANNER_IMAGE_PATH,
    val bioText: String = "",
    val favouriteMovies: List<Movie> = EMPTY_MOVIE_LIST,
    @PrimaryKey
    val id:Int = 1
){
    companion object {
        const val DEFAULT_USERNAME = "Anonymous"
        const val DEFAULT_PROFILE_IMAGE_PATH = "default_profile_image"
        const val DEFAULT_BANNER_IMAGE_PATH = "default_banner_image"
        val EMPTY_MOVIE_LIST = listOf(Movie(), Movie(), Movie(), Movie())
    }
}

//Dummy Data
fun getUserProfileData(): UserProfile {
    return UserProfile(
        username = "@Tylor Durden",
        profileImagePath = "https://github.com/Patch4Code/Logline/assets/116561421/5d417226-4eb2-4f10-a520-d0509519d7c7",
        bannerImagePath = "https://github.com/Patch4Code/Logline/assets/116561421/8f7f93d5-4e8d-4ca9-8a81-f9db06451d5d",
        bioText = "🔥 Welcome to the anti-social club! 🔥\n" +
                "Founder of the real Fight Club.\n" +
                "Connoisseur of fucking chaos.\n" +
                "Soap salesman by day and\n" +
                "revolutionary by night\n" +
                "Reject consumerism. Embrace anarchy.",
        favouriteMovies = listOf(
            Movie("A Clockwork Orange",185, "1971-01-01", "/4sHeTAp65WrSSuc05nRBKddhBxO.jpg"),
            Movie("American Psycho", 1359, "2000-01-01", "/9uGHEgsiUXjCNq8wdq4r49YL8A1.jpg"),
            Movie("Taxi Driver", 103,"1976-01-01","/8FbDLFGRPdWaEz5hWC9wyG0d2il.jpg"),
            Movie("Who Am I", 284427, "1976-01-01","/oR3tbNzJMJmCKS5O5fanU9yxIOk.jpg")
        ),
    )
}