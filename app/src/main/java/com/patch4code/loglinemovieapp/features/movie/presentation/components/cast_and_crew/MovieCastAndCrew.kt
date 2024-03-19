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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.features.movie.domain.model.MovieCredits

@Composable
fun MovieCastAndCrew(movieCredits: MovieCredits?){

    if (movieCredits != null) {
        if(!movieCredits.cast.isNullOrEmpty()){
            Text(text = "Cast", modifier = Modifier.padding(top = 16.dp), fontWeight = FontWeight.Bold)


            var maxIndex by remember { mutableStateOf(5) }
            val scrollState = rememberScrollState()
            Row(
                modifier = Modifier
                    .horizontalScroll(scrollState)
                    .height(IntrinsicSize.Max),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                content = {
                    for (index in 0 until maxIndex) {
                        val castMember = movieCredits.cast[index]
                        CastMemberElement(castMember)
                        if (scrollState.value == scrollState.maxValue) {
                            maxIndex = movieCredits.cast.size
                        }
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

            var maxIndex by remember { mutableStateOf(5) }
            val scrollState = rememberScrollState()
            Row(
                modifier = Modifier
                    .horizontalScroll(scrollState)
                    .height(IntrinsicSize.Max),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                content = {
                    for (index in 0 until maxIndex) {
                        val crewMember = movieCredits.crew[index]
                        CrewMemberElement(crewMember)
                        if (scrollState.value == scrollState.maxValue) {
                            maxIndex = movieCredits.crew.size
                        }
                    }
                },
            )
        }
    }
}