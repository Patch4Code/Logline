package com.patch4code.loglinemovieapp.features.core.presentation.components

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

@Composable
fun ShowToastOnCondition(
    condition: Boolean,
    message: String,
    context: Context = LocalContext.current,
    toastLength: Int = Toast.LENGTH_SHORT
){
    LaunchedEffect(condition) {
        if (condition){
            Toast.makeText(context, message, toastLength).show()
        }
    }
}