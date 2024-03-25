package com.patch4code.loglinemovieapp.features.profile.domain.model

import com.patch4code.loglinemovieapp.features.core.domain.model.Movie

data class UserProfile(
    val username: String,
    val profileImageUrl: String,
    val bannerImageUrl: String,
    val bioText: String,
    val favouriteMovies: List<Movie>,
    val numberOfFriends: Int
)

//Dummy Data
fun getUserProfileData(): UserProfile {
    return UserProfile(
        username = "@Tylor Durden",
        profileImageUrl = "https://github.com/Patch4Code/Logline/assets/116561421/5d417226-4eb2-4f10-a520-d0509519d7c7",
        bannerImageUrl = "https://github.com/Patch4Code/Logline/assets/116561421/8f7f93d5-4e8d-4ca9-8a81-f9db06451d5d",
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
        numberOfFriends = 2
    )
}