package com.example.clickracer.auth.domain.usecases

import com.example.clickracer.auth.domain.repositories.AuthRepository
import javax.inject.Inject

class SignOutUser @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke() {
        authRepository.signOut()
    }
}
