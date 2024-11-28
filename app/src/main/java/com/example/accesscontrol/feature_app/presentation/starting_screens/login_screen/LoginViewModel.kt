package com.example.accesscontrol.feature_app.presentation.starting_screens.login_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): ViewModel() {
    private val _state = mutableStateOf(LoginScreenState())
    val state = _state

    fun onEvent(event: LoginScreenEvents) {
        when(event) {
            is LoginScreenEvents.EnteredEmail -> {
                _state.value = state.value.copy(
                    email = event.value
                )
            }
            is LoginScreenEvents.EnteredPassword -> {
                _state.value = state.value.copy(
                    password = event.value
                )
            }
            is LoginScreenEvents.LoginClicked -> {
                viewModelScope.launch {
                    _state.value = state.value.copy(
                        isLoading = true
                    )
                    authenticateUser(email = state.value.email, password = state.value.password)
                }
            }
            is LoginScreenEvents.ResetPassword -> {
                viewModelScope.launch {
                    _state.value = state.value.copy(
                        isLoading = true,
                        error = null
                    )
                    resetPassword(email = state.value.email)
                }
            }
        }
    }

    private fun authenticateUser(email: String, password: String) {
        if(!isEmailValid(state.value.email)) {
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
                } else {
                    _state.value = state.value.copy(
                        isLoading = false,
                        isLoginSuccess = false,
                        error = task.exception?.message ?: "Login failed. Please try again."
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