package com.moritz.movieappuitest.features.diary.domain.model

import com.moritz.movieappuitest.features.core.domain.model.Movie

data class LoggedMovie(
    val movie: Movie,
    var date: String,
    var rating: Int,
    var review: String = ""
)

var LoggedMoviesDummy: MutableList<LoggedMovie> = mutableListOf(
    LoggedMovie(
        Movie("Willy’s Wonderland", 643586,"2021-01-28","/keEnkeAvifw8NSEC4f6WsqeLJgF.jpg"),
        "2023-11-29",
        6
    ),
    LoggedMovie(
        Movie("Poor Things", 792307,"2023-01-01","/jKyb0MKinNDbHF7WlBP4CE4KPAM.jpg"),
        "2024-01-30",
        9,
        "Cool"
    ),
    LoggedMovie(
        Movie("Old Boy", 670,"2004-09-02","/pWDtjs568ZfOTMbURQBYuT4Qxka.jpg"),
        "2024-01-28",
        10
    ),
    LoggedMovie(
        Movie("Missing", 768362,"2023-02-23","/wEOUYSU5Uf8J7152PT6jdb5233Y.jpg"),
        "2024-01-26",
        8
    ),
    LoggedMovie(
        Movie("Mad Max: Fury Road", 76341,"2015-05-14","/8tZYtuWezp8JbcsvHYO0O46tFbo.jpg"),
        "2024-01-25",
        9,
        "Test"
    ),
    LoggedMovie(
        Movie("The Unbearable Weight of Massive Talent", 648579,"2022-01-28","/aqhLeieyTpTUKPOfZ3jzo2La0Mq.jpg"),
        "2023-12-29",
        7,
        "\"Women - they have minds, and they have souls, as well as just hearts. And they've got ambitions, and they've got talent, as well as just beauty. I'm so sick of people saying that love is just all a woman is fit for. I'm so sick of it.\"\n" +
                "\n" +
                "Ein großer Film über kleine Frauen, die vielleicht gar nicht so klein sind. Vier Schwestern, von denen jede mit individuellen Talenten gesegnet ist, die nicht einfach \"nur\" Frauen sein wollen.\n" +
                "Doch in der Zeit Mitte des 19.Jahrhunderts ist Frauen ein klarer Weg vorgegeben den sie zu gehen habe. Niemand geht seinen eigenen Weg, am wenigsten Frauen: man soll sich gut verheiraten und sich so in die Gesellschaft einfügen.\n" +
                "In 'Little Women' geht es um den Ausbruch aus diesen Dogmen und den damit verbundenen Schwierigkeiten. Es geht um das Lieben und darum geliebt zu werden. Um Verlust, Familie, Freundschaft. Ein Film der tot traurig aber auch so lebensbejahend sein kann.\n" +
                "\n" +
                "Ein Film der dir sagt, dass du deine Träume niemals aufgeben solltest und du deinen eigenen Weg gehen sollst, egal was andere von dir erwarten."
    ),
)