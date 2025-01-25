package com.patch4code.logline.features.movie.presentation.components.cast_and_crew

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.patch4code.logline.R
import com.patch4code.logline.features.core.presentation.utils.TmdbCredentials
import com.patch4code.logline.features.movie.domain.model.Cast
import com.patch4code.logline.features.navigation.domain.model.Screen

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * CastMemberElement - Composable function that Displays a single cast member element, including
 * their profile image, name, and character played.
 * Allows navigation to person's details screen upon clicking.
 *
 * @author Patch4Code
 */
@Composable
fun CastMemberElement(castMember: Cast, navController: NavController) {

    Column (
        modifier = Modifier
            .padding(top = 8.dp)
            .clickable { navController.navigate("${Screen.PersonDetailsScreen.route}/${castMember.id}") },
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        // Display profile image if available
        if(!castMember.profilePath.isNullOrEmpty()){
            AsyncImage(
                model = TmdbCredentials.OTHER_IMAGE_URL + castMember.profilePath,
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
                    .padding(4.dp)
                    .clip(CircleShape)
                    .border(width = 2.dp, color = Color.DarkGray, shape = CircleShape),
                contentDescription = "${stringResource(id = R.string.cast_member_description)} ${castMember.name}",
                contentScale = ContentScale.Crop,
            )
        }
        else{
            // If profile image is not available, display placeholder icon
            Icon(
                imageVector = Icons.Default.AccountCircle,
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
                    .padding(4.dp)
                    .clip(CircleShape)
                    .border(width = 2.dp, color = Color.DarkGray, shape = CircleShape),
                contentDescription = "${stringResource(id = R.string.cast_member_description)} ${castMember.name}"
            )
        }
        Text(text = castMember.name, style = MaterialTheme.typography.bodyMedium)
        Text(text = castMember.character, style = MaterialTheme.typography.bodySmall, modifier = Modifier.width(95.dp), textAlign = TextAlign.Center)
    }
}