package com.patch4code.loglinemovieapp.features.social.presentation.components.social

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * SocialExploreCard - Composable function that displays a SocialExploreCard with a given title and image-vector
 *
 * @author Patch4Code
 */
@Composable
fun SocialExploreCard(onClick:()->Unit, icon: ImageVector, text: String){

    Card(onClick = { onClick() }, modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically){
            Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(50.dp))
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = text, Modifier.weight(1f))
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = null)
        }
    }
}