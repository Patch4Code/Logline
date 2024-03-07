package com.moritz.movieappuitest.features.reviews.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.moritz.movieappuitest.features.core.presentation.utils.JSONHelper.toJson
import com.moritz.movieappuitest.features.core.presentation.utils.MovieHelper
import com.moritz.movieappuitest.features.diary.domain.model.LoggedMovie
import java.net.URLEncoder

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReviewDetailsInfo(loggedElementData: LoggedMovie, onEditPressed:(encodedJsonLoggedItem: String)->Unit){

    val movieTitle = loggedElementData.movie.title
    val movieYear = MovieHelper.extractYear(loggedElementData.movie.releaseDate)
    val rating = loggedElementData.rating.toString()
    val formatedDate = MovieHelper.formatDate(loggedElementData.date)

    Column (modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)){
        Text(text = movieTitle, style = MaterialTheme.typography.titleMedium, maxLines = 2)
        Text(text = movieYear, style = MaterialTheme.typography.bodyMedium)
        Row (
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ){
            Text(text = rating, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.align(
                Alignment.CenterVertically))
            Icon(
                imageVector = Icons.Default.StarRate,
                contentDescription = "StarRate",
                tint = Color.Yellow,
                modifier = Modifier
                    .size(15.dp)
                    .align(Alignment.CenterVertically)
            )
        }
        Text(text = "${formatedDate[0]}. ${formatedDate[1]} ${formatedDate[2]}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.padding(8.dp))

        IconButton(onClick = {
            val jsonLoggedItem = loggedElementData.toJson()
            val encodedJsonLoggedItem = URLEncoder.encode(jsonLoggedItem, "UTF-8")
            onEditPressed(encodedJsonLoggedItem)
        }) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Review")
        }
    }
}