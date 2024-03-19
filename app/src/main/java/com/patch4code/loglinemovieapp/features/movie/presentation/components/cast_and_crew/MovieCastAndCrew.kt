package com.patch4code.loglinemovieapp.features.movie.presentation.components.cast_and_crew

import androidx.compose.runtime.Composable
import com.patch4code.loglinemovieapp.features.movie.domain.model.MovieCredits

@Composable
fun MovieCastAndCrew(movieCredits: MovieCredits?){

    if (movieCredits != null) {
        CreditsSectionList(title = "Cast", items = movieCredits.cast) {
            CastMemberElement(it)
        }
        CreditsSectionList(title = "Crew", items = movieCredits.crew) {
            CrewMemberElement(it)
        }
    }
}