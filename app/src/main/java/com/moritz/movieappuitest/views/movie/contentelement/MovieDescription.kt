package com.moritz.movieappuitest.views.movie.contentelement

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.moritz.movieappuitest.views.general.ExpandableText

@Composable
fun MovieDescription(tagline: String?, overview: String?){

    val movieTagline: String = tagline ?: ""
    val movieDescription: String = overview ?: ""

    Text(text = movieTagline, color = Color.White, modifier = Modifier.padding(top = 8.dp), fontWeight = FontWeight.Bold)
    ExpandableText(text =  movieDescription, maxLinesCollapsed = 3)

    Divider(color = Color.DarkGray, thickness = 1.dp, modifier = Modifier
        .fillMaxWidth()
        .padding(top = 16.dp, bottom = 16.dp))
}