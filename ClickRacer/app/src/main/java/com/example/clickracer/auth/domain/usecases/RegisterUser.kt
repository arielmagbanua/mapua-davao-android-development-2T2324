package com.example.clickracer.auth.domain.usecases

import com.example.clickracer.auth.domain.repositories.AuthRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import javax.inject.Inject

class RegisterUser @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(
        email: String,
        password: String,
        onRegistrationComplete: (task: Task<AuthResult>) -> Unit
    ) {
        authRepository.register(
            email = email,
            password = password,
            onRegistrationComplete = onRegistrationComplete
        )
    }
}
