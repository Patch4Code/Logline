package com.patch4code.loglinemovieapp.features.social.presentation.components.social.account_card

import android.widget.Toast
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.patch4code.loglinemovieapp.features.social.presentation.screen_social.SocialViewModel

@Composable
fun SocialAccountCardLogoutButton(socialViewModel: SocialViewModel){

    val context = LocalContext.current

    Button(
        onClick = {
            socialViewModel.logout(
                onLogoutSuccessful = { Toast.makeText(context, "Successful Logged Out", Toast.LENGTH_LONG).show() },
                onLogoutError = { Toast.makeText(context, "Logout Error: ${it!!.message}", Toast.LENGTH_LONG).show() }
            )
        }
    ) {
        Text(text = "Log Out")
    }
}