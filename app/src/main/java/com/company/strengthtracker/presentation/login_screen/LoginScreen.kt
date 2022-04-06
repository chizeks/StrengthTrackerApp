package com.company.strengthtracker.presentation.login_screen

import androidx.compose.foundation.Image
import com.company.strengthtracker.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.InternalCoroutinesApi
import androidx.hilt.navigation.compose.hiltViewModel


/*

LOGIN SCREEN
Has an identifier text field, password text field, register button, and (todo)? forgot password

 */

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    /* COLUMNS (Also can use rows) */
    // This column fills all nested composables to the entire size of the screen and centers
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // This column limits nested composables to 80% of the screen width
        Column(
            modifier = Modifier.fillMaxWidth(0.8f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            /* TEXT FIELDS */
            // On remember mutableStateOf(): https://dev.to/zachklipp/remember-mutablestateof-a-cheat-sheet-10ma
            var userIdText by remember { mutableStateOf("") }
            var userPassText by remember { mutableStateOf("") }
            Box(modifier = Modifier.fillMaxWidth(0.5f)) {
                Image(
                    painter = painterResource(id = R.drawable.gigachad),
                    contentDescription = "Giga Chad"
                )
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = userIdText,
                        onValueChange = { userIdText = it },
                        label = { Text("UserID") }
                    )
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = userPassText,
                        onValueChange = { userPassText = it },
                        label = { Text("Password") }
                    )
                    /* BUTTONS */
                    // Login
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        onClick = {
                            // TODO
                            // Take controller parameter and navigate to a register screen
                        }
                    ) {
                        Text(
                            text = "Login"
                        )
                    }
                    // Register
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            // TODO
                            // Take controller parameter and navigate to a register screen
                        }
                    ) {
                        Text(
                            text = "Register"
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Forgot Password
                Text(
                    text = "Forgot Your Password?",
                    modifier = Modifier.clickable {
                        // TODO
                        // Navigate
                    }
                )
            }
        }
    }
}