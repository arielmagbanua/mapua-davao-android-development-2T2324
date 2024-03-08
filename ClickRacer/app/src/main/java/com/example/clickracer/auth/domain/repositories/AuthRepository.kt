package com.example.clickracer.auth.domain.repositories

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    fun getCurrentUser(): FirebaseUser?
    fun signOut()
}
