package com.example.clickracer.auth.domain.usecases

import com.example.clickracer.auth.domain.repositories.AuthRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class GetCurrentUser @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(): FirebaseUser? {
        return authRepository.getCurrentUser()
    }
}
