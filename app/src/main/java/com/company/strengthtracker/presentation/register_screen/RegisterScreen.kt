package com.company.strengthtracker.ui.register_screen

import android.icu.text.CaseMap
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.company.strengthtracker.R
import com.company.strengthtracker.presentation.login_screen.LoginViewModel
import com.company.strengthtracker.presentation.register_screen.RegisterViewModel

@Composable
fun RegisterScreen(
    navController: NavController,
    registerViewModel: RegisterViewModel = hiltViewModel()
) {
    /* COLUMNS (Also can use rows) */
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
                    onClick = { /*TODO*/
                        //navigate to placeholder
                    }

                ) {
                    Text(text = "Create Account")
                }

            }
        }
    }
}

