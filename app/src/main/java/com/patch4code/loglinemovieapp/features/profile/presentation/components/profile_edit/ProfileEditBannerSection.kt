package com.patch4code.loglinemovieapp.features.profile.presentation.components.profile_edit

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
import coil.compose.AsyncImage
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.profile.domain.model.UserProfile

@Composable
fun ProfileEditBannerSection(userProfile: UserProfile?){

    val context = LocalContext.current

    val bannerImagePath = userProfile?.bannerImagePath

    Column {
        Text(text = "Banner Image", style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(8.dp))
        Card (onClick = { /*TODO*/ },
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
                .border(width = 2.dp, color = Color.DarkGray)
        ){
            AsyncImage(
                model = bannerImagePath,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                contentDescription = "Banner",
                error = painterResource(id = R.drawable.default_banner_image)
            )
        }
        TextButton(onClick = { /*TODO*/ }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "Reset to Default")
        }
    }
}