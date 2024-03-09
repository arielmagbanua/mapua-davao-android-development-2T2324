package com.example.clickracer.auth.ui

import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import com.example.clickracer.auth.data.datastates.AuthState
import com.example.clickracer.auth.domain.usecases.GetCurrentUser
import com.example.clickracer.auth.domain.usecases.GoogleSignInClient
import com.example.clickracer.auth.domain.usecases.GoogleSignInTask
import com.example.clickracer.auth.domain.usecases.RegisterUser
import com.example.clickracer.auth.domain.usecases.SignInWithEmailAndPassword
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
import com.google.android.gms.auth.api.signin.GoogleSignInClient as GSC

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val getCurrentUser: GetCurrentUser,
    private val signOutUser: SignOutUser,
    private val registerUser: RegisterUser,
    private val googleSignInClient: GoogleSignInClient,
    private val googleSignInTask: GoogleSignInTask,
    private val signInWithEmailAndPassword: SignInWithEmailAndPassword
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
        setCurrentUser(null)
    }

    fun register(
        email: String,
        password: String,
        onRegistrationComplete: (task: Task<AuthResult>) -> Unit
    ) {
        registerUser(email = email, password = password) { task ->
            onRegistrationComplete(task)
            logout() // logout immediately
        }
    }

    fun googleSign(result: ActivityResult, onGoogleSignIn: (FirebaseUser?) -> Unit) {
        googleSignInTask(result) {currentUser ->
            onGoogleSignIn(currentUser)
            setCurrentUser(currentUser)
        }
    }

    fun createGoogleSignInClient(): GSC {
        return googleSignInClient()
    }

    fun signIn(email: String, password: String) {
        signInWithEmailAndPassword(email = email, password = password) { currentUser ->
            setCurrentUser(FirebaseAuth.getInstance().currentUser)
        }
    }
}
