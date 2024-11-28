package com.example.accesscontrol.feature_app.presentation.starting_screens.register_screen

sealed class RegisterScreenEvents {
    data class EnteredEmail(val value: String): RegisterScreenEvents()
    data class EnteredPassword(val value: String): RegisterScreenEvents()
    object RegisterClicked: RegisterScreenEvents()
}