package com.patch4code.loglinemovieapp.features.social.presentation.components.social.account_card

import android.widget.Toast
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.patch4code.loglinemovieapp.R
import com.patch4code.loglinemovieapp.features.social.presentation.screen_social.SocialViewModel

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * SocialAccountCardLogoutButton - Composable function that displays the logout button for the SocialAccountCard.
 * Calls the ViewModel to trigger logout-process on click.
 *
 * @author Patch4Code
 */
@Composable
fun SocialAccountCardLogoutButton(socialViewModel: SocialViewModel){

    val context = LocalContext.current

    val logoutSuccessToastText = stringResource(id = R.string.logout_success_toast)
    val logoutErrorToastText = stringResource(id = R.string.logout_error_toast)

    Button(
        onClick = {
            socialViewModel.logout(
                onLogoutSuccessful = { Toast.makeText(context, logoutSuccessToastText, Toast.LENGTH_LONG).show() },
                onLogoutError = { Toast.makeText(context, "$logoutErrorToastText ${it!!.message}", Toast.LENGTH_LONG).show() }
            )
        }
    ) {
        Text(text = stringResource(id = R.string.logout_text))
    }
}