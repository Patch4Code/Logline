package com.patch4code.loglinemovieapp.features.profile.presentation.components.profile_edit

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.features.profile.domain.model.UserProfile

@Composable
fun ProfileEditBannerSection(userProfile: UserProfile?){

    val context = LocalContext.current

    val bannerImageName = userProfile?.bannerImagePath ?: UserProfile.DEFAULT_BANNER_IMAGE_PATH
    val bannerImageResourceId = context.resources.getIdentifier(bannerImageName, "drawable", context.packageName)

    Column {
        Text(text = "Banner Image", style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(8.dp))
        Card (onClick = { /*TODO*/ },
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
                .border(width = 2.dp, color = Color.DarkGray)
        ){
            Image(
                painter = painterResource(bannerImageResourceId),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                contentDescription = "Banner")
        }
        TextButton(onClick = { /*TODO*/ }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "Reset to Default")
        }
    }
}