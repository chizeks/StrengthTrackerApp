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

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {

    val isLoading by remember { viewModel.isLoading }
    val endReached by remember { viewModel.endReached }
    val error by remember { viewModel.hasError }
    val message by remember { viewModel.message }

    // They pushed register and the view model is now loading
    if (isLoading) {
        Text(text = message + " is loading")
    }
    // Error in registration
    // Inform user why and prompt them to register again
    else if (endReached && error) {
        Text(text = message + " end reached")
        Button(onClick = {
            viewModel.reset()
        }) {
            Text("click to reset")
        }
    }
    // Successful registration
    // Should navigate user back to login screen
    else if (endReached && !error) {
        Text(text = message + " successful registration")
        Button(onClick = {
            navController.navigate(Screen.LoginScreen.route)
        }) {
            Text("click to return to login screen")
        }
    }
    // Else, present initial registration UI
    else
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
                                viewModel.registerUser(
                                    email = newUserEmail,
                                    password = newUserPassText,
                                    username = newUserId
                                )
                            } else {
                                passErr = "Passwords do not match."
                                passConfirmation = ""
                                newUserPassText = ""
                            }
                        }

                    ) {
                        Text(text = "Create Account")
                    }

                }
            }
        }
}

