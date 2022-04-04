package com.company.strengthtracker.ui.login_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/*

LOGIN SCREEN
Has an identifier text field, password text field, register button, and (todo)? forgot password

 */

@Composable
fun LoginScreen() {
    /* COLUMNS (Also can use rows) */
    // This column fills all nested composables to the entire size of the screen and centers
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // This column limits nested composables to 80% of the screen width
        Column(
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            /* TEXT FIELDS */
            // On remember mutableStateOf(): https://dev.to/zachklipp/remember-mutablestateof-a-cheat-sheet-10ma
            var userIdText by remember { mutableStateOf("") }
            var userPassText by remember { mutableStateOf("") }
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
            // Forgot Password
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    // TODO
                    // Take controller parameter and navigate to a register screen
                }
            ) {
                Text(
                    text = "Forgot Password"
                )
            }
        }
    }
}