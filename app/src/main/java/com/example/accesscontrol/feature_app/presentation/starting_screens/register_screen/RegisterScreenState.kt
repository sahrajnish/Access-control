package com.example.accesscontrol.feature_app.presentation.starting_screens.register_screen

data class RegisterScreenState (
    val email: String = "",
    val password: String = "",
    val error: String? = null,
    val isRegisterSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val message: String? = null
)