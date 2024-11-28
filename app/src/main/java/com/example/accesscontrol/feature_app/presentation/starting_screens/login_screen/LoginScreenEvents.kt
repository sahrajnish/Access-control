package com.example.accesscontrol.feature_app.presentation.starting_screens.login_screen

sealed class LoginScreenEvents {
    data class EnteredEmail(val value: String): LoginScreenEvents()
    data class EnteredPassword(val value: String): LoginScreenEvents()
    object LoginClicked: LoginScreenEvents()
    object ResetPassword: LoginScreenEvents()
}