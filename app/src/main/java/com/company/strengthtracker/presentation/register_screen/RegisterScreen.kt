package com.company.strengthtracker.presentation.register_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.company.strengthtracker.R
import com.company.strengthtracker.Screen
import com.company.strengthtracker.presentation.register_screen.RegisterViewModel.RegisterScreenState.*

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {

    val screenState by remember { viewModel.registerScreenState }

    when (screenState) {
        LOADING -> {
            Text(text = "LOADING")
        }
        REGISTER_SUCCESS -> {
            Column {
                Text(text = "successful registration")
                Button(onClick = {
                    navController.navigate(Screen.LoginScreen.route) {
                        popUpTo(Screen.LoginScreen.route) {
                            inclusive = true
                        }
                    }
                }) {
                    Text("click to return to login screen")
                }
            }
        }
        REGISTER_ERROR -> {
            Column {
                Text(text = "ERROR")
                Button(onClick = {
                    viewModel.reset()
                }) {
                    Text("click to reset")
                }
            }
        }
        // Present registration UI
        LAUNCH -> {

<<<<<<< HEAD
            var newUserEmail by remember { mutableStateOf("") }
            var newUserDob by remember { mutableStateOf("") }
            var newUserId by remember { mutableStateOf("") }
            var newUserPassText by remember { mutableStateOf("") }
            var passConfirmation by remember { mutableStateOf("") }
            var passErr by remember { mutableStateOf("Create a New Account") }
            var passwordVis by remember { mutableStateOf(false) }

            Box(modifier = Modifier.fillMaxWidth(0.5f)) {
                Image(
                    painter = painterResource(id = R.drawable.gigachad),
                    contentDescription = "Giga Chad"
                )
            }
            Card(modifier = Modifier.fillMaxWidth(0.8f)){
                Card(modifier = Modifier.fillMaxWidth(0.4f)) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                      Text(text = passErr)
                    }
                }
            }
=======
            /* COLUMNS */
            // This column fills all nested composables to the entire size of the screen and centers
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                var newUserEmail by remember { mutableStateOf("") }
                var newUserDob by remember { mutableStateOf("") }
                var newUserId by remember { mutableStateOf("") }
                var newUserPassText by remember { mutableStateOf("") }
                var passConfirmation by remember { mutableStateOf("") }
                var passwordVis by remember { mutableStateOf(false) }
>>>>>>> main

                Box(modifier = Modifier.fillMaxWidth(0.5f)) {
                    Image(
                        painter = painterResource(id = R.drawable.gigachad),
                        contentDescription = "Giga Chad"
                    )
                }

                Card(modifier = Modifier.fillMaxWidth(0.8f)) {
                    Column(
                        modifier = Modifier.padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            value = newUserDob,
                            onValueChange = { newUserDob = it },
                            label = { Text("Date of birth") }
                        )
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            value = newUserEmail,
                            onValueChange = { newUserEmail = it },
                            label = { Text("Email") }
                        )
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = newUserId,
                            onValueChange = { newUserId = it },
                            label = { Text("Username") }
                        )

<<<<<<< HEAD
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = if (passwordVis) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        value = newUserPassText,

                        onValueChange = {
                            newUserPassText = it
                        },
                        label = { Text("Password") }
                    )
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = if (passwordVis) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        value = passConfirmation,
                        onValueChange = {
                            passConfirmation = it
                        },
                        label = { Text("Confirm password") }
                    )
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            if (newUserPassText.equals(passConfirmation)) {
=======

                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            visualTransformation = if (passwordVis) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            value = newUserPassText,
                            onValueChange = { newUserPassText = it },
                            label = { Text("Password") }
                        )
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            visualTransformation = if (passwordVis) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            value = passConfirmation,
                            onValueChange = { passConfirmation = it },
                            label = { Text("Confirm password") }
                        )
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
>>>>>>> main
                                viewModel.registerUser(
                                    email = newUserEmail,
                                    password = newUserPassText,
                                    username = newUserId
                                )
<<<<<<< HEAD
                            } else {
                                passErr = "Passwords do not match."
                                passConfirmation = ""
                                newUserPassText = ""
                            }
=======
                            }

                        ) {
                            Text(text = "Create Account")
>>>>>>> main
                        }

                    }
                }
            }
        }
    }
}

