package com.magbanua.todo.auth.ui

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.magbanua.todo.auth.data.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AuthViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(AuthState())
    val uiState: StateFlow<AuthState> = _uiState.asStateFlow()

    fun loggedIn(): Boolean {
        val currentUser = _uiState.value.currentUser;
        return currentUser != null;
    }

    fun setCurrentUser(user: FirebaseUser) {
        // update the current user state
        _uiState.update { currentState -> currentState.copy(
            currentUser = user)
        }
    }
}