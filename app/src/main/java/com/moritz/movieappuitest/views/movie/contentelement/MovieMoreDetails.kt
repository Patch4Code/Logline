package com.moritz.movieappuitest.views.movie.contentelement

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
import androidx.compose.material3.Divider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.moritz.movieappuitest.dataclasses.ProductionCompany
import com.moritz.movieappuitest.dataclasses.ProductionCountry
import com.moritz.movieappuitest.dataclasses.SpokenLanguage

@Composable
fun MovieMoreDetails(
    studios: List<ProductionCompany>?,
    countries: List<ProductionCountry>?,
    budget: Int?,
    revenue: Long?,
    status: String?,
    languages: List<SpokenLanguage>?
){

    val movieStatus: String = status ?: "N/A"

    var showDetails by remember { mutableStateOf(false) }

    Spacer(modifier = Modifier.padding(16.dp))
    Divider(color = Color.DarkGray, thickness = 1.dp, modifier = Modifier
        .fillMaxWidth()
        .padding(top = 16.dp, bottom = 16.dp)
    )

    Card (
        modifier = Modifier
            .animateContentSize(animationSpec = tween(100))
            .clickable {
                showDetails = !showDetails
            }
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
    ){
        Row (modifier = Modifier.padding(8.dp).fillMaxWidth()){
            Text(text = "More Details", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
            Icon(
                imageVector = if(showDetails) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = "ArrowDown"
            )
        }

        Column (modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)){
            if(showDetails){

                //Production Companies
                Text(text = "Production Companies:", color = Color.White, modifier = Modifier.padding(bottom = 4.dp),style = MaterialTheme.typography.titleSmall)
                Row {
                    studios?.let {
                        Text(text = it.joinToString { studio -> studio.name }, color = Color.White, style = MaterialTheme.typography.bodyMedium)
                    }
                }
                Spacer(modifier = Modifier.padding(16.dp))


                //Countries
                Text(text = "Production Countries:", color = Color.White, modifier = Modifier.padding(bottom = 4.dp),style = MaterialTheme.typography.titleSmall)
                Row {
                    countries?.let {
                        Text(text = it.joinToString { country -> country.name }, color = Color.White, style = MaterialTheme.typography.bodyMedium)
                    }
                }
                Spacer(modifier = Modifier.padding(16.dp))

                //Spoken Languages
                Text(text = "Spoken Languages:", color = Color.White, modifier = Modifier.padding(bottom = 4.dp),style = MaterialTheme.typography.titleSmall)
                Row {
                    languages?.let {
                        Text(text = it.joinToString { language -> language.englishName }, color = Color.White, style = MaterialTheme.typography.bodyMedium)
                    }
                }
                Spacer(modifier = Modifier.padding(16.dp))

                //Status
                Text(text = "Status:", color = Color.White, modifier = Modifier.padding(bottom = 4.dp),style = MaterialTheme.typography.titleSmall)
                Text(text = movieStatus, color = Color.White, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.padding(8.dp))

                //Budget
                Text(text = "Budget:", color = Color.White, modifier = Modifier.padding(bottom = 4.dp),style = MaterialTheme.typography.titleSmall)
                Text(text = "$budget $", color = Color.White, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.padding(8.dp))

                //Revenue
                Text(text = "Revenue:", color = Color.White, modifier = Modifier.padding(bottom = 4.dp),style = MaterialTheme.typography.titleSmall)
                Text(text = "$revenue $", color = Color.White, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.padding(8.dp))
            }
        }

    }
    Spacer(modifier = Modifier.padding(8.dp))
}