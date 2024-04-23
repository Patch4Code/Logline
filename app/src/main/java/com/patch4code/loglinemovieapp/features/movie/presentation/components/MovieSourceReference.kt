package com.patch4code.loglinemovieapp.features.movie.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieSourceReference(){

    val scope = rememberCoroutineScope()
    val tooltipState = rememberTooltipState(isPersistent = true)

    HorizontalDivider(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))
    Row(verticalAlignment = Alignment.CenterVertically){
        Text(text = stringResource(id = R.string.source_title))
        Text(text = "TMDB", fontStyle = FontStyle.Italic)
        TooltipBox(
            positionProvider =  TooltipDefaults.rememberPlainTooltipPositionProvider(),
            tooltip = {
                Card (modifier = Modifier.padding(8.dp)){
                    Text(text = stringResource(id = R.string.tmdb_credits_text),
                        modifier = Modifier.padding(8.dp)) }
                },
            state = tooltipState
        ) {
            IconButton(onClick = { scope.launch { tooltipState.show() } }) {
                Icon(imageVector = Icons.Outlined.Info, contentDescription = stringResource(id = R.string.source_tooltip_description))
            }
        }
    }
}