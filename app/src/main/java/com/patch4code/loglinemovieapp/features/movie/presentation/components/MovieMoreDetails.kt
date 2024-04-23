package com.patch4code.loglinemovieapp.features.movie.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.movie.domain.model.MovieDetails
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

@Composable
fun MovieMoreDetails(movieDetails: MovieDetails?
){

    val movieStatus: String = movieDetails?.status ?: "N/A"

    var showDetails by remember { mutableStateOf(false) }

    val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US) as DecimalFormat
    currencyFormat.applyPattern("#,###")

    val formatedBudget =
        if((movieDetails?.budget ?: 0) > 0)
            currencyFormat.format(movieDetails?.budget?.toDouble() ?: 0.0) + " $"
        else "N/A"
    val formatedRevenue =
        if ((movieDetails?.revenue ?: 0) > 0)
            currencyFormat.format(movieDetails?.revenue?.toDouble() ?: 0.0) + " $"
        else "N/A"

    HorizontalDivider(modifier = Modifier.padding(top = 32.dp, bottom = 16.dp))

    Card (
        modifier = Modifier
            .animateContentSize(animationSpec = tween(100))
            .clickable {
                showDetails = !showDetails
            }
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray,
        ),
    ){
        Row (modifier = Modifier.padding(8.dp).fillMaxWidth()){
            Text(text = stringResource(id = R.string.more_details_title), modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
            Icon(
                imageVector = if(showDetails) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null
            )
        }

        Column (modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)){
            if(showDetails){

                //Production Companies
                Text(text = stringResource(id = R.string.production_companies_title),
                    modifier = Modifier.padding(bottom = 4.dp),
                    style = MaterialTheme.typography.titleSmall
                )
                Row {
                    movieDetails?.productionCompanies?.let {
                        Text(text = it.joinToString { studio -> studio.name }, style = MaterialTheme.typography.bodyMedium)
                    }
                }
                Spacer(modifier = Modifier.padding(16.dp))


                //Countries
                Text(text = stringResource(id = R.string.production_countries_title),
                    modifier = Modifier.padding(bottom = 4.dp),
                    style = MaterialTheme.typography.titleSmall
                )
                Row {
                    movieDetails?.productionCountries?.let {
                        Text(text = it.joinToString { country -> country.name }, style = MaterialTheme.typography.bodyMedium)
                    }
                }
                Spacer(modifier = Modifier.padding(16.dp))

                //Spoken Languages
                Text(text = stringResource(id = R.string.spoken_languages_title), modifier = Modifier.padding(bottom = 4.dp),style = MaterialTheme.typography.titleSmall)
                Row {
                    movieDetails?.spokenLanguages?.let {
                        Text(text = it.joinToString { language -> language.englishName }, style = MaterialTheme.typography.bodyMedium)
                    }
                }
                Spacer(modifier = Modifier.padding(16.dp))

                //Status
                Text(text = stringResource(id = R.string.status_title), modifier = Modifier.padding(bottom = 4.dp),style = MaterialTheme.typography.titleSmall)
                Text(text = movieStatus, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.padding(8.dp))

                //Budget
                Text(text = stringResource(id = R.string.budget_title), modifier = Modifier.padding(bottom = 4.dp),style = MaterialTheme.typography.titleSmall)
                Text(text = formatedBudget, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.padding(8.dp))

                //Revenue
                Text(text = stringResource(id = R.string.revenue_title), modifier = Modifier.padding(bottom = 4.dp),style = MaterialTheme.typography.titleSmall)
                Text(text = formatedRevenue, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.padding(8.dp))
            }
        }
    }
    Spacer(modifier = Modifier.padding(8.dp))
}