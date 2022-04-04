package com.company.strengthtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController
import com.company.strengthtracker.ui.login_screen.LoginScreen
import com.company.strengthtracker.ui.theme.StrengthTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StrengthTrackerTheme {
                // NavController contains methods to change navigation
                // So if we ever want to navigate within a screen, it must be passed as a parameter
                val navController = rememberNavController()
                // NavHost defines all possible screens
                NavHost(
                    navController = navController,
                    startDestination = Screen.LoginScreen.route
                ) {
                    // Each composable() represents a Screen; pass in composable which represent screen
                    composable(route = Screen.LoginScreen.route) {
                        LoginScreen()
                    }
                    composable(route = Screen.RegisterScreen.route) {
                        // TODO
                        // PASS IN COMPOSABLE THAT REPRESENTS REGISTER SCREEN
                    }
                }

            }
        }
    }
}
