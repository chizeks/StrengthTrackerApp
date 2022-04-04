package com.company.strengthtracker

// Specify screens which we have in the application
sealed class Screen(val route: String) {
    object LoginScreen : Screen("login_screen")
    object RegisterScreen : Screen("register_screen")
}
