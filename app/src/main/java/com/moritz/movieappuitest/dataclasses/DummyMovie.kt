package com.moritz.movieappuitest.dataclasses

data class DummyMovie(
    val title: String = "Example Movie",
    val year: Int = 0,
    val posterUrl: String = "",
    val director: String = "",
    val length: Int = 0,
    val genre: List<String> = listOf(),
    val description: String = ""
)
{
    fun getHomeMoviesDummy(): Map<String, List<DummyMovie>> {
        return mapOf(
            "New Movies" to listOf(
                DummyMovie("Poor Things", 2023, "https://xl.movieposterdb.com/23_08/2023/14230458/xl_poor-things-movie-poster_7d09c673.jpeg","Yorgos Lanthimos", 141, listOf("Romance", "Science Fiction", "Comedy"), "Brought back to life by an unorthodox scientist, a young woman runs off with a lawyer on a whirlwind adventure across the continents. Free from the prejudices of her times, she grows steadfast in her purpose to stand for equality and liberation."),
                DummyMovie("Mean Girls", 2024, "https://xl.movieposterdb.com/23_11/2024/11762114/xl_mean-girls-movie-poster_ab9845ae.jpg"),
                DummyMovie("The Beekeeper", 2024, "https://xl.movieposterdb.com/23_10/2024/15314262/xl_the-beekeeper-movie-poster_4075f58c.jpg"),
                DummyMovie("Wonka", 2023, "https://posters.movieposterdb.com/24_01/2023/6166392/l_wonka-movie-poster_7f3d2b2e.jpg"),
                DummyMovie("Migration", 2023, "https://posters.movieposterdb.com/23_10/2023/6495056/l_migration-movie-poster_a9ece6e0.jpg"),
                DummyMovie("Aquaman and the Lost Kingdom", 2023, "https://xl.movieposterdb.com/23_11/2023/9663764/xl_aquaman-and-the-lost-kingdom-movie-poster_fb816864.jpg"),
            ),
            "New from Friends" to listOf(
                DummyMovie("Mad Max: Fury Road", 2015, "https://xl.movieposterdb.com/15_07/2015/1392190/xl_1392190_50142848.jpg"),
                DummyMovie("The Banshees of Inisherin", 2022, "https://xl.movieposterdb.com/23_04/2022/11813216/xl_the-banshees-of-inisherin-movie-poster_e2668375.jpg"),
                DummyMovie("Limbo", 2021, "https://xl.movieposterdb.com/22_07/2021/14032696/xl_14032696_25962eba.jpg"),
                DummyMovie("The Grand Budapest Hotel", 2014, "https://xl.movieposterdb.com/14_08/2014/2278388/xl_2278388_52b919fb.jpg"),
                DummyMovie("Searching", 2018, "https://xl.movieposterdb.com/21_02/2020/6723592/xl_6723592_a3c6809d.jpg"),
                DummyMovie("Saltburn", 2023, "https://xl.movieposterdb.com/23_10/0/17351924/xl_saltburn-movie-poster_6f9e747f.jpg"),
            ),
            "Top Movies" to listOf(
                DummyMovie("The Shawshank Redemption", 1994, "https://xl.movieposterdb.com/05_03/1994/0111161/xl_8494_0111161_3bb8e662.jpg"),
                DummyMovie("The Godfather", 1972, "https://xl.movieposterdb.com/22_07/1972/68646/xl_68646_8c811dec.jpg"),
                DummyMovie("The Dark Knight", 2008, "https://xl.movieposterdb.com/08_05/2008/468569/xl_468569_f0e2cd63.jpg"),
            )
        )
    }
}


