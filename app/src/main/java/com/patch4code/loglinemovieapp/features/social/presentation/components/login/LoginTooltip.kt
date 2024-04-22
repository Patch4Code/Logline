package com.patch4code.loglinemovieapp.features.social.presentation.components.login

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.ui.theme.SonicSilver
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginTooltip(){

    val scope = rememberCoroutineScope()
    val tooltipState = rememberTooltipState(isPersistent = true)

    TooltipBox(
        positionProvider =  TooltipDefaults.rememberPlainTooltipPositionProvider(),
        tooltip = {
            Card (modifier = Modifier.padding(8.dp), colors = CardDefaults.cardColors(containerColor = SonicSilver)){
                Text(text = "Log in to access the social features of the Logline app, " +
                        "allowing you to publish lists and reviews, and enabling others " +
                        "to view your profile.",
                    modifier = Modifier.padding(8.dp)) }
        },
        state = tooltipState
    ) {
        IconButton(onClick = { scope.launch { tooltipState.show() } }) {
            Icon(imageVector = Icons.Outlined.Info, contentDescription = "more info")
        }
    }
}