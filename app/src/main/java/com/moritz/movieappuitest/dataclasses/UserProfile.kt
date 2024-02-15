package com.moritz.movieappuitest.dataclasses

data class UserProfile(
    val username: String,
    val profileImageUrl: String,
    val bannerImageUrl: String,
    val bioText: String,
    val favouriteMovies: List<Movie>,
    val numberOfFriends: Int
)

//Dummy Data
fun getUserProfileData(): UserProfile{
    return UserProfile(
        username = "@Tylor Durden",
        profileImageUrl = "https://media.gq-magazin.de/photos/5c9cdb109d77084a7e9ed707/1:1/w_800,h_800,c_limit/teaser-fight-club-quer.jpg",
        bannerImageUrl = "https://64.media.tumblr.com/60d40dcf8b890fa9278e6d0658b80f72/58e5a6f8f2be5db3-58/s1280x1920/40cbce0f93616aa0ead8ca3c6367d3c26ac3e82e.jpg",
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
            Movie("Who Am I", 284427, "1976-01-01","/oR3tbNzJMJmCKS5O5fanU9yxIOk.jpg",)
        ),
        numberOfFriends = 2
    )
}