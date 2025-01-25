package com.patch4code.logline.features.profile.presentation.components.profile

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.patch4code.logline.R
import com.patch4code.logline.features.profile.domain.model.UserProfile

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * ProfileHead - Composable function for displaying the profile header.
 * It consists of the banner image and the circular profile image.
 *
 * @author Patch4Code
 */
@Composable
fun ProfileHead(userProfile: UserProfile?){

    val profileImagePath = userProfile?.profileImagePath
    val bannerImagePath = userProfile?.bannerImagePath

    Box {
        AsyncImage(
            model = bannerImagePath,
            modifier = Modifier
                .fillMaxWidth()
                .height(125.dp),
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(id = R.string.banner_image_description),
            error = painterResource(id = R.drawable.default_banner_image)
        )

        AsyncImage(
            model = profileImagePath,
            modifier = Modifier
                .height(120.dp)
                .width(120.dp)
                .align(Alignment.Center)
                .offset(y = 60.dp)
                .clip(CircleShape)
                .border(width = 2.dp, color = Color.DarkGray, shape = CircleShape),
            contentDescription = stringResource(id = R.string.profile_image_title),
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.default_profile_image)
        )
    }
}