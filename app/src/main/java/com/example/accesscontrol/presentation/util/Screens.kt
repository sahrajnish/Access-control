package com.example.accesscontrol.presentation.util

sealed class Screens(val route: String) {
    object WelcomeScreen: Screens(route = "welcome_screen")
    object LoginScreen: Screens(route = "login_screen")
    object RegisterScreen: Screens(route = "register_screen")
    object HomeScreen: Screens(route = "home_screen")
}