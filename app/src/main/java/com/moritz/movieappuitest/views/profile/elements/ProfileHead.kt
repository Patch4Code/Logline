package com.moritz.movieappuitest.views.profile.elements

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.moritz.movieappuitest.dataclasses.UserProfile

@Composable
fun ProfileHead(userData: MutableState<UserProfile>){
    Box {
        AsyncImage(
            model = userData.value.bannerImageUrl,
            contentDescription = "Banner"
        )
        AsyncImage(
            model = userData.value.profileImageUrl,
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