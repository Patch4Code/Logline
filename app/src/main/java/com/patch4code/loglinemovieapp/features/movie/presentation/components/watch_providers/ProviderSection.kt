package com.patch4code.loglinemovieapp.features.movie.presentation.components.watch_providers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.patch4code.loglinemovieapp.features.core.presentation.utils.TmdbCredentials
import com.patch4code.loglinemovieapp.features.movie.domain.model.Provider

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ProviderSection - Composable function that displays the section for a watch provider element
 * (streaming or rent/buy)
 *
 * @author Patch4Code
 */
@Composable
fun ProviderSection(providerList: List<Provider>?, title: String){

    if (providerList != null){
        Column (modifier = Modifier.padding(4.dp)) {
            // Title of the provider section
            Text(text = title, modifier = Modifier.padding(bottom = 4.dp),style = MaterialTheme.typography.titleSmall)
            Row {
                var maxIndex = -1
                // Display up to four provider icons
                providerList.forEachIndexed { index, provider ->
                    maxIndex = index
                    if (index < 4){
                        AsyncImage(
                            model = TmdbCredentials.OTHER_IMAGE_URL + provider.logoPath,
                            modifier = Modifier
                                .size(30.dp)
                                .padding(2.dp)
                                .clip(CircleShape),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                // Display a "+" indicator if there are more than four watch providers
                if(maxIndex > 4){
                    Card (modifier = Modifier
                        .size(30.dp)
                        .padding(2.dp)
                        .clip(CircleShape),
                        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
                    ){
                        Text(text = "+${maxIndex-4}", modifier = Modifier.align(Alignment.CenterHorizontally))
                    }
                }
            }
        }
    }
}