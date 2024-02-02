package com.moritz.movieappuitest.views

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.moritz.movieappuitest.dataclasses.DummyMovie
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class LoggedMovie(
    val movie: DummyMovie,
    val date: LocalDate,
    val rating: Int
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DiaryView(navController: NavController){

    val dummyLogs: List<LoggedMovie> = listOf(
        LoggedMovie(
            DummyMovie("Poor Things", 2023,"https://xl.movieposterdb.com/23_08/2023/14230458/xl_poor-things-movie-poster_7d09c673.jpeg"),
            LocalDate.parse("2023-01-19", DateTimeFormatter.ISO_DATE),
            9
            ),
        LoggedMovie(
            DummyMovie("Poor Things", 2023,"https://xl.movieposterdb.com/23_08/2023/14230458/xl_poor-things-movie-poster_7d09c673.jpeg"),
            LocalDate.parse("2023-12-19", DateTimeFormatter.ISO_DATE),
            9
        ),
        LoggedMovie(
            DummyMovie("Poor Things", 2023,"https://xl.movieposterdb.com/23_08/2023/14230458/xl_poor-things-movie-poster_7d09c673.jpeg"),
            LocalDate.parse("2023-12-19", DateTimeFormatter.ISO_DATE),
            9
        ),
        LoggedMovie(
            DummyMovie("Poor Things", 2023,"https://xl.movieposterdb.com/23_08/2023/14230458/xl_poor-things-movie-poster_7d09c673.jpeg"),
            LocalDate.parse("2023-12-19", DateTimeFormatter.ISO_DATE),
            9
        ),
        LoggedMovie(
            DummyMovie("Poor Things", 2023,"https://xl.movieposterdb.com/23_08/2023/14230458/xl_poor-things-movie-poster_7d09c673.jpeg"),
            LocalDate.parse("2023-12-19", DateTimeFormatter.ISO_DATE),
            9
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        items(dummyLogs) { loggedItem ->
            LoggedMovieItem(loggedElement = loggedItem)
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoggedMovieItem(loggedElement: LoggedMovie) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(8.dp)
    ){
        AsyncImage(
            model = loggedElement.movie.posterUrl,
            contentDescription = "${loggedElement.movie.title}-Poster"
        )
        Column (modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .width(140.dp)){
            Text(text = loggedElement.movie.title, style = MaterialTheme.typography.titleMedium, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = loggedElement.movie.year.toString(), style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Row (
                modifier = Modifier.padding(4.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ){
                Icon(
                    imageVector = Icons.Default.StarRate,
                    contentDescription = "StarRate",
                    tint = Color.Yellow,
                    modifier = Modifier
                        .size(15.dp)
                        .align(CenterVertically)
                )
                Text(text = "${loggedElement.rating}", color = Color.White, modifier = Modifier.align(CenterVertically))
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        val parsedDate = formatDate(loggedElement.date)
        Column (modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally){
            parsedDate[1]?.let { Text(text = it, style = MaterialTheme.typography.titleMedium) }
            parsedDate[0]?.let { Text(text = it, style = MaterialTheme.typography.headlineLarge) }
            parsedDate[2]?.let { Text(text = it, style = MaterialTheme.typography.titleSmall, color = Color.Gray) }
            formatDate(loggedElement.date)
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(date: LocalDate): List<String?> {
    val monthMap = mapOf(
        1 to "JAN",
        2 to "FEB",
        3 to "MAR",
        4 to "APR",
        5 to "MAY",
        6 to "JUN",
        7 to "JUL",
        8 to "AUG",
        9 to "SEP",
        10 to "OCT",
        11 to "NOV",
        12 to "DEC"
    )
    Log.e("Date",date.toString())

    return listOf(date.dayOfMonth.toString(), monthMap[date.monthValue], date.year.toString())//date.format(formatter)
}