package com.magbanua.todo.auth.data

import com.google.firebase.auth.FirebaseUser

data class AuthState (
    val currentUser: FirebaseUser? = null
)
