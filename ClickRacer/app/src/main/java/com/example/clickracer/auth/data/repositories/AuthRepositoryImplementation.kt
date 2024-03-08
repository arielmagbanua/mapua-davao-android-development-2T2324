package com.example.clickracer.auth.data.repositories

import com.example.clickracer.auth.domain.repositories.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthRepositoryImplementation : AuthRepository {
    override fun getCurrentUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }

    override fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }
}
