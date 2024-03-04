package com.magbanua.todo.auth.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.magbanua.todo.auth.data.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AuthViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(AuthState())
    val uiState: StateFlow<AuthState> = _uiState.asStateFlow()

    init {
        val currentUser = FirebaseAuth.getInstance().currentUser;
        setCurrentUser(currentUser)
    }

    fun setCurrentUser(user: FirebaseUser? = null) {
        // update the current user state
        _uiState.update { currentState -> currentState.copy(
            currentUser = user)
        }
    }

    fun logout() {
        // sign-out
        FirebaseAuth.getInstance().signOut()

        // set the current user to null
        setCurrentUser(null)
    }

    fun register(
        email: String,
        password: String,
        onRegistrationComplete: (task: Task<AuthResult>) -> Unit
    ) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task -> onRegistrationComplete(task)}
    }

    fun signIn(email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign-in successful
                    // set the current user
                    setCurrentUser(FirebaseAuth.getInstance().currentUser)
                } else {
                    // Sign-in failed
                    // Handle exception and display error message
                    Log.e("SIGN_IN", "Something went wrong!")
                }
            }
    }
}
