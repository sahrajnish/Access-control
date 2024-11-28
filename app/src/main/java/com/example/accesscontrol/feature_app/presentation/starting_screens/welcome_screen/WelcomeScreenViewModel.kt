package com.example.accesscontrol.feature_app.presentation.starting_screens.welcome_screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.accesscontrol.feature_app.presentation.starting_screens.login_screen.LoginScreenState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeScreenViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): ViewModel() {
    private val _state = mutableStateOf(LoginScreenState())
    val state = _state

    init {
        checkSignedInUser()
    }

    private fun checkSignedInUser() {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            viewModelScope.launch {
                try {
                    Log.d("inspect", "Inside welcome viewmodel")
                    _state.value = state.value.copy(
                        isLoginSuccess = true
                    )
                } catch (e: Exception) {
                    _state.value = state.value.copy(
                        error = "Failed to fetch user data."
                    )
                }
            }
        } else {
            _state.value = state.value.copy(
                isLoginSuccess = false
            )
        }
    }

}