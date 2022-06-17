package com.company.strengthtracker.presentation.exercise_select_screen.ui

import androidx.compose.foundation.Image
import com.company.strengthtracker.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.company.strengthtracker.Screen
import com.company.strengthtracker.presentation.login_screen.LoginViewModel.LoginScreenState.*

/*

LOGIN SCREEN
Has an identifier text field, password text field, register button, and (todo)? forgot password

LOGIC:
The first thing that occurs on load is a check to see if user is already logged in.
If so, navigate them to home screen.

 */

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {

}