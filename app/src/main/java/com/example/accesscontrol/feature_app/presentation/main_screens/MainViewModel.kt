package com.example.accesscontrol.feature_app.presentation.main_screens

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.accesscontrol.feature_app.domain.use_case.AppUseCases
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val appUseCase: AppUseCases
): ViewModel() {

    private val _state = mutableStateOf(MainState())
    val state: State<MainState> = _state

    private val firebaseUser = firebaseAuth.currentUser
    private val userId = firebaseUser?.uid

    init {
        retrieveUser(userId)
    }

    fun onEvent(event: MainEvents) {
        when(event) {
            is MainEvents.SignOut -> {
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

    private fun retrieveUser(userId: String?) {
        viewModelScope.launch {
            try {
                val user = userId?.let { appUseCase.getUser(it) }
                _state.value = state.value.copy(
                    user = user
                )
            } catch (e: Exception) {
                _state.value = state.value.copy(
                    error = "failed to fetch user. ${e.message}"
                )
            }
        }
    }
}