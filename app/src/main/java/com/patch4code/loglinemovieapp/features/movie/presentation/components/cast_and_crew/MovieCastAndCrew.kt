package com.patch4code.loglinemovieapp.features.movie.presentation.components.cast_and_crew

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.features.movie.domain.model.MovieCredits

@Composable
fun MovieCastAndCrew(movieCredits: MovieCredits?){

    if (movieCredits != null) {
        if(!movieCredits.cast.isNullOrEmpty()){
            Text(text = "Cast", modifier = Modifier.padding(top = 16.dp), fontWeight = FontWeight.Bold)

            Row(modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .height(IntrinsicSize.Max),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                content = {
                   for(castMember in movieCredits.cast){
                       CastMemberElement(castMember)
                   }
                },
            )
        }
        if(!movieCredits.crew.isNullOrEmpty()){
            Text(
                text = "Crew",
                modifier = Modifier.padding(top = 4.dp),
                fontWeight = FontWeight.Bold
            )
            Row(modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .height(IntrinsicSize.Max),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                content = {
                    for(crewMember in movieCredits.crew){
                        CrewMemberElement(crewMember)
                    }
                },
            )
        }
    }
}