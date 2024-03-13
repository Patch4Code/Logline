package com.patch4code.loglinemovieapp.features.movie.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.patch4code.loglinemovieapp.features.core.presentation.components.ExpandableText

@Composable
fun MovieDescription(tagline: String?, overview: String?){

    val movieTagline: String = tagline ?: ""
    val movieDescription: String = overview ?: ""

    Text(text = movieTagline, color = Color.White, modifier = Modifier.padding(top = 8.dp), fontWeight = FontWeight.Bold)
    ExpandableText(text =  movieDescription, maxLinesCollapsed = 3)

    HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))
}