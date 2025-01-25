package com.patch4code.logline.features.settings.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.patch4code.logline.R
import com.patch4code.logline.features.navigation.domain.model.Screen
import com.patch4code.logline.ui.theme.Beige

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * EditProfileButton - Composable function to display a button that navigates to the ProfileEditView
 *
 * @author Patch4Code
 */
@Composable
fun EditProfileButton(navController: NavController){

    HorizontalDivider()
    TextButton(onClick = { navController.navigate(Screen.ProfileEditScreen.route) },
        modifier = Modifier.fillMaxWidth(),
        shape = RectangleShape
    ) {
        Column (modifier = Modifier.padding(8.dp)){
            Row {
                Text(text = stringResource(id = R.string.edit_profile_title), style = MaterialTheme.typography.titleMedium)
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = null, modifier = Modifier.size(20.dp))
            }
            Text(text = stringResource(id = R.string.edit_profile_text),
                style = MaterialTheme.typography.bodyMedium,
                color = Beige
            )
        }
    }
}