package com.moritz.movieappuitest.features.movie.presentation.components.cast_and_crew

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.moritz.movieappuitest.features.core.presentation.utils.TmdbCredentials
import com.moritz.movieappuitest.features.movie.domain.model.Crew

@Composable
fun CrewMemberElement(crewMember: Crew){
    Column (modifier = Modifier.padding(top = 8.dp, bottom = 8.dp), horizontalAlignment = Alignment.CenterHorizontally){
        if(!crewMember.profilePath.isNullOrEmpty()){
            AsyncImage(
                model = TmdbCredentials.OTHER_IMAGE_URL + crewMember.profilePath,
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
                    .padding(4.dp)
                    .clip(CircleShape)
                    .border(width = 2.dp, color = Color.DarkGray, shape = CircleShape),
                contentDescription = "Crew Member Image of ${crewMember.name}",
                contentScale = ContentScale.Crop
            )
        }
        else{
            Icon(
                imageVector = Icons.Default.AccountCircle,
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
                    .padding(4.dp)
                    .clip(CircleShape)
                    .border(width = 2.dp, color = Color.DarkGray, shape = CircleShape),
                contentDescription = "Cast Member Image of ${crewMember.name}"
            )
        }
        Text(text = crewMember.name, style = MaterialTheme.typography.bodyMedium)
        Text(text = crewMember.job, style = MaterialTheme.typography.bodySmall, modifier = Modifier.width(95.dp), textAlign = TextAlign.Center)
    }
}