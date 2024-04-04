package com.patch4code.loglinemovieapp.features.profile.presentation.components

import androidx.compose.foundation.Image
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.features.profile.domain.model.UserProfile

@Composable
fun ProfileHead(userProfile: UserProfile?){

    val context = LocalContext.current

    val profileImageName = userProfile?.profileImagePath ?: UserProfile.DEFAULT_PROFILE_IMAGE_PATH
    val bannerImageName = userProfile?.bannerImagePath ?: UserProfile.DEFAULT_BANNER_IMAGE_PATH

    val profileImageResourceId = context.resources.getIdentifier(profileImageName, "drawable", context.packageName)
    val bannerImageResourceId = context.resources.getIdentifier(bannerImageName, "drawable", context.packageName)

    Box {
        Image(
            painter = painterResource(bannerImageResourceId),
            modifier = Modifier.fillMaxWidth().height(125.dp),
            contentScale = ContentScale.Crop,
            contentDescription = "Banner"
        )

        Image(
            painter = painterResource(id = profileImageResourceId),
            modifier = Modifier
                .height(120.dp)
                .width(120.dp)
                .align(Alignment.Center)
                .offset(y = 60.dp)
                .clip(CircleShape)
                .border(width = 2.dp, color = Color.DarkGray, shape = CircleShape),
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop
        )
    }
}