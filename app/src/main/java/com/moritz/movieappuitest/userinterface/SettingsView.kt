package com.moritz.movieappuitest.userinterface

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun SettingsView(navController: NavController){
    Column()
    {
        Text(text = "Settings View")
    }
}