package com.example.accesscontrol.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.accesscontrol.presentation.welcome_screens.TextFieldState
import com.example.accesscontrol.presentation.welcome_screens.WelcomeScreenViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): ViewModel() {

    private val _state = mutableStateOf(TextFieldState())
    val state: State<TextFieldState> = _state

    val loginState = _state.value.copy(
        isLoginSuccess = true
    )

    fun onEvent(event: AppEvents) {
        when(event) {
            is AppEvents.SignOut -> {
                viewModelScope.launch {
                    try {
                        firebaseAuth.signOut()
                    } catch (e: Exception) {
                        _state.value = state.value.copy(
                            error = "Failed to sign out: ${e.message}"
                        )
                    }
                }
            }
        }
    }
}