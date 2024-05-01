package com.patch4code.loglinemovieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.patch4code.loglinemovieapp.features.navigation.presentation.screen_navigation.Navigation
import com.patch4code.loglinemovieapp.room_database.LoglineDatabase
import com.patch4code.loglinemovieapp.ui.theme.LoglineUiTheme

/**
 * GNU GENERAL PUBLIC LICENSE, VERSION 3.0 (https://www.gnu.org/licenses/gpl-3.0.html)
 *
 * MainActivity - Entry point of the App.
 *
 * @author Patch4Code
 */
class MainActivity : ComponentActivity() {

    // Initializes the database instance
    private val db by lazy {
        Room.databaseBuilder(
            context = applicationContext,
            klass = LoglineDatabase::class.java,
            name = "loglineData.db"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Applies the LoglineUiTheme to the entire activity
            LoglineUiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Initialize the navigation graph with the database
                    Navigation(db)
                }
            }
        }
    }
}