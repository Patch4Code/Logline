package com.patch4code.loglinemovieapp.features.social.presentation.components.social

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.preferences_datastore.StoreUserData
import com.patch4code.loglinemovieapp.features.social.presentation.components.social.account_card.SocialAccountCardInfo
import com.patch4code.loglinemovieapp.features.social.presentation.components.social.account_card.SocialAccountCardLogoutButton
import com.patch4code.loglinemovieapp.features.social.presentation.components.social.account_card.SocialAccountCardProfileState
import com.patch4code.loglinemovieapp.features.social.presentation.screen_social.SocialViewModel

@Composable
fun SocialAccountCard(savedLoginData: StoreUserData, socialViewModel: SocialViewModel){

    Card {
        Column (modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            SocialAccountCardInfo(savedLoginData)
            SocialAccountCardProfileState(savedLoginData, socialViewModel)
            SocialAccountCardLogoutButton(socialViewModel)
        }
    }
}