package com.example.accesscontrol.feature_app.presentation.starting_screens.login_screen

data class LoginScreenState (
    val email: String = "",
    val password: String = "",
    val error: String? = null,
    val isLoginSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val message: String? = null
)