package com.example.clickracer.auth.data.repositories

import android.util.Log
import com.example.clickracer.auth.domain.repositories.AuthRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthRepositoryImplementation : AuthRepository {
    override fun getCurrentUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }

    override fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }

    override fun register(
        email: String,
        password: String,
        onRegistrationComplete: (task: Task<AuthResult>) -> Unit
    ) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task -> onRegistrationComplete(task) }
    }

    override fun signInWithEmailAndPassword(email: String, password: String, onSignIn: (FirebaseUser?) -> Unit) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign-in successful
                    // set the current user
                    onSignIn(FirebaseAuth.getInstance().currentUser)
                } else {
                    // Sign-in failed
                    // Handle exception and display error message
                    Log.e("SIGN_IN", "Something went wrong!")
                }
            }
    }

    override fun firebaseSignIn(credential: AuthCredential, onGoogleSignIn: (FirebaseUser?) -> Unit) {
        // sign in with Firebase using the credential
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { signInTask ->
                if (signInTask.isSuccessful) {
                    // Handle successful sign-in to Firebase
                    val currentUser = FirebaseAuth.getInstance().currentUser
                    onGoogleSignIn(currentUser);

                    Log.d("FIREBASE_SIGN_IN", currentUser?.email.toString())
                } else {
                    // Handle sign-in failure
                    Log.e("FIREBASE_SIGN_IN", "Something went wrong!")
                }
            }
    }
}
