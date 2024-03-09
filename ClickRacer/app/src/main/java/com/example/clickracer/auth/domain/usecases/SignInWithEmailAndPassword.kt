package com.example.clickracer.auth.domain.usecases

import com.example.clickracer.auth.domain.repositories.AuthRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class SignInWithEmailAndPassword @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(email: String, password: String, onSignIn: (FirebaseUser?) -> Unit) {
        authRepository.signInWithEmailAndPassword(
            email = email,
            password = password,
            onSignIn = onSignIn
        )
    }
}
