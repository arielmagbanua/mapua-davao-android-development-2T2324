package com.example.clickracer.auth.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.clickracer.auth.data.datastates.AuthState
import com.example.clickracer.auth.domain.usecases.GetCurrentUser
import com.example.clickracer.auth.domain.usecases.SignOutUser
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val getCurrentUser: GetCurrentUser,
    private val signOutUser: SignOutUser
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthState())
    val uiState: StateFlow<AuthState> = _uiState.asStateFlow()

    init {
        setCurrentUser(getCurrentUser())
    }

    fun setCurrentUser(user: FirebaseUser? = null) {
        // update the current user state
        _uiState.update { currentState ->
            currentState.copy(
                currentUser = user
            )
        }
    }

    fun logout() {
        signOutUser()
    }

    fun register(
        email: String,
        password: String,
        onRegistrationComplete: (task: Task<AuthResult>) -> Unit
    ) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                onRegistrationComplete(task)
                logout() // logout immediately
            }
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
