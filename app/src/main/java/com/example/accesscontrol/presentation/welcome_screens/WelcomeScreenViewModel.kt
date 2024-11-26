package com.example.accesscontrol.presentation.welcome_screens

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.accesscontrol.presentation.AppEvents
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeScreenViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): ViewModel() {
    private val _state = mutableStateOf(TextFieldState())
    val state: State<TextFieldState> = _state

    fun onEvent(event: WelcomeScreensEvents) {
        when(event) {
            is WelcomeScreensEvents.EnteredEmail -> {
                _state.value = state.value.copy(
                    email = event.value
                )
            }
            is WelcomeScreensEvents.EnteredPassword -> {
                _state.value = state.value.copy(
                    password = event.value
                )
            }
            is WelcomeScreensEvents.LoginClicked -> {
                viewModelScope.launch {
                    _state.value = state.value.copy(
                        isLoading = true
                    )
                    authenticateUser(state.value.email, state.value.password)
                }
            }

            is WelcomeScreensEvents.RegisterClicked -> {
                viewModelScope.launch {
                    _state.value = state.value.copy(
                        isLoading = true
                    )
                    registerUser(state.value.email, state.value.password)
                }
            }

            is WelcomeScreensEvents.ResetPassword -> {
                viewModelScope.launch {
                    _state.value = state.value.copy(
                        isLoading = true,
                        error = null
                    )
                    resetPassword(state.value.email)
                }
            }
        }
    }

    private fun authenticateUser(email: String, password: String) {
        if(!isEmailValid(email)) {
            _state.value = state.value.copy(
                isLoading = false,
                error = "Invalid email format"
            )
            return
        }

        if(!isPasswordValid(password)) {
            _state.value = state.value.copy(
                isLoading = false,
                error = "Password must be at least 8 characters"
            )
            return
        }

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _state.value = state.value.copy(
                        isLoading = false,
                        error = null,
                        isLoginSuccess = true
                    )
                    Log.d("home", "homeScreen")
                    Log.d("home", "${state.value.isLoginSuccess}")
                } else {
                    _state.value = state.value.copy(
                        isLoading = false,
                        isLoginSuccess = false,
                        error = task.exception?.message ?: "Login failed. Please try again."
                    )
                }
            }
    }

    private fun registerUser(email: String, password: String) {
        if(!isEmailValid(email)) {
            _state.value = state.value.copy(
                isLoading = false,
                error = "Invalid email format"
            )
            return
        }

        if(!isPasswordValid(password)) {
            _state.value = state.value.copy(
                isLoading = false,
                error = "Password must be at least 8 characters"
            )
            return
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task->
                if (task.isSuccessful) {
                    _state.value = state.value.copy(
                        isLoading = false,
                        isRegisterSuccess = true,
                        isLoginSuccess = true,
                        error = null
                    )
                } else {
                    _state.value = state.value.copy(
                        isLoading = false,
                        error = task.exception?.message ?: "Something went wrong. Please try again."
                    )
                }
            }
    }

    private fun resetPassword(email: String) {
        if(!isEmailValid(email)) {
            _state.value = state.value.copy(
                isLoading = false,
                error = "Invalid email format"
            )
            return
        }

        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task->
                _state.value = state.value.copy(isLoading = false)
                if (task.isSuccessful) {
                    _state.value = state.value.copy(
                        message = "If this email is registered, a password reset email has been sent. Check your inbox."
                    )
                } else {
                    val exception = task.exception
                    if (exception is FirebaseAuthInvalidUserException) {
                        _state.value = state.value.copy(
                            error = task.exception?.message ?: "User not found."
                        )
                    } else {
                        _state.value = state.value.copy(
                            error = "Error sending password reset email."
                        )
                    }
                }
            }
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 8
    }
}