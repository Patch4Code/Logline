package com.patch4code.loglinemovieapp.features.profile.presentation.components.profile_edit

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
fun ProfileEditImageSection(userProfile: UserProfile?){

    val context = LocalContext.current

    val profileImageName = userProfile?.profileImagePath ?: UserProfile.DEFAULT_PROFILE_IMAGE_PATH
    val profileImageResourceId = context.resources.getIdentifier(profileImageName, "drawable", context.packageName)

    Column(horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = "Profile Image", style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(8.dp))
        Card(onClick = { /*TODO*/ },
            modifier = Modifier
                .height(120.dp)
                .width(120.dp)
                .clip(CircleShape)
                .border(width = 2.dp, color = Color.DarkGray, shape = CircleShape)
        ){
            Image(
                painter = painterResource(id = profileImageResourceId),
                contentDescription = "Profile Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        TextButton(onClick = { /*TODO*/ }) {
            Text(text = "Reset to Default")
        }
    }
    Spacer(modifier = Modifier.padding(8.dp))
}