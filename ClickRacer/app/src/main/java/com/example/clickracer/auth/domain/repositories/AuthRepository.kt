package com.example.clickracer.auth.domain.repositories

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    fun getCurrentUser(): FirebaseUser?

    fun signOut()

    fun register(
        email: String,
        password: String,
        onRegistrationComplete: (task: Task<AuthResult>) -> Unit
    )

    fun signInWithEmailAndPassword(email: String, password: String, onSignIn: (FirebaseUser?) -> Unit)

    fun firebaseSignIn(credential: AuthCredential, onGoogleSignIn: (FirebaseUser?) -> Unit)
}
