package com.example.accesscontrol.presentation.welcome_screens

data class TextFieldState(
    val email: String = "",
    val password: String = "",
    val error: String? = null,
    val isLoginSuccess: Boolean = false,
    val isRegisterSuccess: Boolean = false,
    val isSignedOut: Boolean = false,
    val isLoading: Boolean = false,
    val message: String? = null
)