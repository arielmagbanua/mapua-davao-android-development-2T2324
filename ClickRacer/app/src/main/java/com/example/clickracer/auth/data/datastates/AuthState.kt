package com.example.clickracer.auth.data.datastates

import com.google.firebase.auth.FirebaseUser

data class AuthState (
    val currentUser: FirebaseUser? = null
)
