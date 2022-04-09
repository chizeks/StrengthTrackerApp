package com.company.strengthtracker.presentation.welcome_screen

import androidx.compose.foundation.Image
import com.company.strengthtracker.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.company.strengthtracker.Screen
import com.company.strengthtracker.presentation.login_screen.LoginViewModel

/*


 */

@Composable
fun WelcomeScreen(
    navController: NavController,
    viewModel: WelcomeViewModel = hiltViewModel()
) {


    val isLoading by remember { viewModel.isLoading }
    val currentUser by remember { viewModel.currentUser }
    val endReached by remember { viewModel.endReached }
    val hasError by remember { viewModel.hasError }

    when {
        // Display loading message
        isLoading -> {
            Text("is loading")
        }
        // User is already logged-in initially OR logged-in successfully
        hasError -> {
            Text("Error occured")
        }
        currentUser != null -> {
            Column {
                Text(currentUser!!.email)
                Text(currentUser!!.uid)
                Text(currentUser!!.username)
            }
        }
        endReached -> {
            Text("bruh not sure if this is reachable")
        }
    }
}