package com.patch4code.loglinemovieapp.features.movie.presentation.components.cast_and_crew

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.movie.domain.model.MovieCredits

@Composable
fun MovieCastAndCrew(movieCredits: MovieCredits?, navController: NavController){

    if (movieCredits != null) {
        CreditsSectionList(title = stringResource(id = R.string.cast_title), items = movieCredits.cast) {
            CastMemberElement(it, navController)
        }
        CreditsSectionList(title = stringResource(id = R.string.crew_title), items = movieCredits.crew) {
            CrewMemberElement(it, navController)
        }
    }
}