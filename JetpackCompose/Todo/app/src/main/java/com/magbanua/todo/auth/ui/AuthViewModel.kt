package com.magbanua.todo.auth.ui

import androidx.lifecycle.ViewModel
import com.magbanua.todo.auth.data.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AuthViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(AuthState())
    val uiState: StateFlow<AuthState> = _uiState.asStateFlow()

    fun loggedIn() {
        _uiState.update { currentState ->
            currentState.copy(
                isLoggedIn = true
            )
        }
    }

    fun logOut() {
        _uiState.update { currentState ->
            currentState.copy(
                isLoggedIn = false
            )
        }
    }
}