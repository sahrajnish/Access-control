package com.example.accesscontrol.feature_app.presentation.starting_screens.register_screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.accesscontrol.feature_app.domain.model.AppUser
import com.example.accesscontrol.feature_app.domain.use_case.AppUseCases
import com.example.accesscontrol.feature_app.presentation.starting_screens.login_screen.LoginScreenState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val appUseCase: AppUseCases
): ViewModel() {
    private val _state = mutableStateOf(RegisterScreenState())
    val state = _state

    private val _loginState = mutableStateOf(LoginScreenState())
    val loginState = _loginState

    fun onEvent(event: RegisterScreenEvents) {
        when(event) {
            is RegisterScreenEvents.EnteredEmail -> {
                _state.value = state.value.copy(
                    email = event.value
                )
            }
            is RegisterScreenEvents.EnteredPassword -> {
                _state.value = state.value.copy(
                    password = event.value
                )
            }
            is RegisterScreenEvents.RegisterClicked -> {
                viewModelScope.launch {
                    _state.value = state.value.copy(
                        isLoading = true,
                    )
                    registerUser(email = state.value.email, password = state.value.password)
                }
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
                    val firebaseUser = firebaseAuth.currentUser
                    if (firebaseUser != null) {
                        val user = AppUser(id = firebaseUser.uid, email = firebaseUser.email)
                        addUserToFirestore(user, firebaseUser.uid)
                    }
                    _state.value = state.value.copy(
                        isLoading = false,
                        isRegisterSuccess = true,
                        error = null
                    )
                    _loginState.value = loginState.value.copy(
                        isLoginSuccess = true
                    )
                } else {
                    _state.value = state.value.copy(
                        isLoading = false,
                        error = task.exception?.message ?: "Something went wrong. Please try again."
                    )
                }
            }
    }

    private fun addUserToFirestore(user: AppUser, userId: String) {
        viewModelScope.launch {
            try {
                appUseCase.addUser(user.copy(id = userId))
            } catch (e: Exception) {
                Log.e("RegisterScreenViewModel", "Error adding user: $e")
                _state.value = state.value.copy(
                    isLoading = false,
                    error = "Failed to save user to Firestore. Please try again."
                )
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