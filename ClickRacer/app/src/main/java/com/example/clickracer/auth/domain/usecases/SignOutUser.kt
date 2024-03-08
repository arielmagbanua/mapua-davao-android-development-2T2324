package com.example.clickracer.auth.domain.usecases

import com.example.clickracer.auth.domain.repositories.AuthRepository

class SignOutUser(private val authRepository: AuthRepository) {
    operator fun invoke() {
        authRepository.signOut()
    }
}