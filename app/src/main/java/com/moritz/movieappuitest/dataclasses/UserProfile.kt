package com.moritz.movieappuitest.dataclasses

data class UserProfile(
    val username: String,
    val profileImageUrl: String,
    val bannerImageUrl: String,
    val bioText: String,
    val favouriteMovies: List<DummyMovie>,
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
            DummyMovie("A Clockwork Orange", 1971 , "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/4sHeTAp65WrSSuc05nRBKddhBxO.jpg",),
            DummyMovie("American Psycho", 2000 , "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/9uGHEgsiUXjCNq8wdq4r49YL8A1.jpg",),
            DummyMovie("Taxi Driver", 1976  , "https://xl.movieposterdb.com/21_06/1976/75314/xl_75314_a3d1031e.jpg",),
            DummyMovie("Taxi Driver", 1976  , "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oR3tbNzJMJmCKS5O5fanU9yxIOk.jpg",)
        ),
        numberOfFriends = 2
    )
}