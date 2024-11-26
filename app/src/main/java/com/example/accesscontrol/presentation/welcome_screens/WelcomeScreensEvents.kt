package com.example.accesscontrol.presentation.welcome_screens

sealed class WelcomeScreensEvents {
    data class EnteredEmail(val value: String): WelcomeScreensEvents()
    data class EnteredPassword(val value: String): WelcomeScreensEvents()
    object LoginClicked: WelcomeScreensEvents()
    object RegisterClicked: WelcomeScreensEvents()
    object ResetPassword: WelcomeScreensEvents()
}