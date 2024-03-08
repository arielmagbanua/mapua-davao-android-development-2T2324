package com.example.clickracer.auth.domain.usecases

import androidx.activity.result.ActivityResult
import com.example.clickracer.auth.domain.repositories.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import javax.inject.Inject

class GoogleSignInTask @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(result: ActivityResult, onGoogleSignIn: (FirebaseUser?) -> Unit): Task<GoogleSignInAccount> {
        return GoogleSignIn.getSignedInAccountFromIntent(result.data).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val account = task.result!!
                val idToken = account.idToken!!
                val credential = GoogleAuthProvider.getCredential(idToken, null)

                authRepository.firebaseSignIn(credential) {
                    onGoogleSignIn(it)
                }
            }
        }
    }
}
