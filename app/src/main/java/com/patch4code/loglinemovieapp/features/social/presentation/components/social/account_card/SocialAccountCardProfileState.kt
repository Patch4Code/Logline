package com.patch4code.loglinemovieapp.features.social.presentation.components.social.account_card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.PublicOff
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.parse.ParseUser
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.navigation.domain.model.Screen
import com.patch4code.loglinemovieapp.features.social.presentation.screen_social.SocialViewModel
import com.patch4code.loglinemovieapp.preferences_datastore.StoreUserData

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * SocialAccountCardProfileState - Composable function that displays a button to view your profile
 * and shows the profile state (private/public).
 *
 * @author Patch4Code
 */
@Composable
fun SocialAccountCardProfileState(savedLoginData: StoreUserData, socialViewModel: SocialViewModel, navController: NavController){

    val openMakePrivateDialog = remember { mutableStateOf(false)  }
    val openMakePublicDialog = remember { mutableStateOf(false)  }
    val openUpdateDialog = remember { mutableStateOf(false)  }

    val currentUser = ParseUser.getCurrentUser()
    val profileId = currentUser.objectId
    val userName = currentUser.username

    Column {

        // navigates to the current users profile (PublicProfileView)
        TextButton(onClick = { navController.navigate(Screen.PublicProfileScreen.route + "/$profileId/$userName") }) {
            Text(text = stringResource(id = R.string.view_your_profile_text))
        }

        // Display profile state (public/private) and buttons to change the state and to update the profile
        Row (verticalAlignment = Alignment.CenterVertically){
            if(savedLoginData.getIsProfilePublic.collectAsState(initial = false).value == true){
                Text(text = stringResource(id = R.string.profile_public_text), Modifier.weight(1f), style = MaterialTheme.typography.titleMedium)

                TextButton(onClick = { openUpdateDialog.value = true }) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                }

                TextButton(onClick = { openMakePrivateDialog.value = true }) {
                    Icon(imageVector = Icons.Default.Public, contentDescription = null)
                }
            }else{
                Text(text = stringResource(id = R.string.profile_private_text), Modifier.weight(1f), style = MaterialTheme.typography.titleMedium)
                TextButton(onClick = { openMakePublicDialog.value = true }) {
                    Icon(imageVector = Icons.Default.PublicOff, contentDescription = null)
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }


    SocialMakePrivateDialog(openMakePrivateDialog, socialViewModel)
    SocialMakePublicDialog(openMakePublicDialog, socialViewModel)
    SocialUpdateDialog(openUpdateDialog, socialViewModel)
}