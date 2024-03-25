package com.patch4code.loglinemovieapp.features.social.presentation.components.social

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FeaturedPlayList
import androidx.compose.material.icons.filled.Groups2
import androidx.compose.material.icons.filled.Reviews
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.datastore.StoreUserData
import com.patch4code.loglinemovieapp.features.social.presentation.screen_social.SocialViewModel

@Composable
fun SocialContent(savedLoginData: StoreUserData, socialViewModel: SocialViewModel){

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {

            SocialAccountCard(savedLoginData, socialViewModel)

            Spacer(modifier = Modifier.padding(4.dp))

            SocialExploreCard(
                onClick = {},
                icon = Icons.Default.Groups2,
                text = "Explore Public Accounts"
            )
            SocialExploreCard(
                onClick = {},
                icon = Icons.Default.Reviews,
                text = "Explore Public Reviews"
            )
            SocialExploreCard(
                onClick = {},
                icon = Icons.AutoMirrored.Filled.FeaturedPlayList,
                text = "Explore Public Lists"
            )
        }
    }
}