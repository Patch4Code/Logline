package com.patch4code.loglinemovieapp.features.social.presentation.components.social

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.patch4code.loglinemovieapp.features.social.presentation.components.social.account_card.SocialAccountCardInfo
import com.patch4code.loglinemovieapp.features.social.presentation.components.social.account_card.SocialAccountCardLogoutButton
import com.patch4code.loglinemovieapp.features.social.presentation.components.social.account_card.SocialAccountCardProfileState
import com.patch4code.loglinemovieapp.features.social.presentation.screen_social.SocialViewModel
import com.patch4code.loglinemovieapp.preferences_datastore.StoreUserData

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * SocialAccountCard - Composable function that displays the account card of the SocialView.
 * Displays account info (username and e-mail), action buttons to view your profile, making the
 * profile public/private and updating the profile as well as a logout button.
 *
 * @author Patch4Code
 */
@Composable
fun SocialAccountCard(savedLoginData: StoreUserData, socialViewModel: SocialViewModel, navController: NavController){

    Card {
        Column (modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            SocialAccountCardInfo(savedLoginData)
            SocialAccountCardProfileState(savedLoginData, socialViewModel, navController)
            SocialAccountCardLogoutButton(socialViewModel)
        }
    }
}