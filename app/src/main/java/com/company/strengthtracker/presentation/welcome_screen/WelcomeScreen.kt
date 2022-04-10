package com.company.strengthtracker.presentation.welcome_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.company.strengthtracker.Screen
import com.company.strengthtracker.presentation.welcome_screen.WelcomeViewModel.WelcomeScreenState.*

/*


 */

@Composable
fun WelcomeScreen(
    navController: NavController,
    viewModel: WelcomeViewModel = hiltViewModel()
) {

    val welcomeScreenState by remember { viewModel.welcomeScreenState }

    // User Entity from users collection
    val currentUser by remember { viewModel.currentUser }

    when (welcomeScreenState) {
        // Display loading message
        LOADING -> {
            Text("is loading welcome screen")
        }
        // User is already logged-in initially OR logged-in successfully
        ERROR -> {
            Text("Error occurred")
        }
        CONNECTED -> {
            Column {
                Text(currentUser!!.email)
                Text(currentUser!!.uid)
                Text(currentUser!!.username)
                Button(
                    onClick = { viewModel.logout() }
                ) {
                    Text("Logout")
                }
            }
        }
        // Disconnected, return to login-screen
        DISCONNECTED -> {
            navController.popBackStack(Screen.WelcomeScreen.route, true)
            navController.navigate(Screen.LoginScreen.route)
        }
        // This currently shouldn't ever happen - probably remove in future
        LAUNCH -> {

        }
    }
}