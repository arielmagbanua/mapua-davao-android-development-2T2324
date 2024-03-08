package com.example.clickracer.auth.ui

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.clickracer.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

@Composable
fun LoginScreen(
    context: Context,
    onLogin: (String, String) -> Unit,
    onGoogleSignIn: (currentUser: FirebaseUser?) -> Unit,
    onRegistrationClick: () -> Unit
) {
    // State variables for username and password
    val emailState = rememberSaveable { mutableStateOf("") }
    val passwordState = rememberSaveable { mutableStateOf("") }

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id)).requestEmail().build()

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                // Handle successful sign-in in the `onComplete` callback below

                task.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val account = task.result!!
                        val idToken = account.idToken!!
                        val credential = GoogleAuthProvider.getCredential(idToken, null)

                        // Sign in with Firebase using the credential
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
                    } else {
                        // Handle sign-in failure
                        Log.e("GOOGLE_SIGN_IN", "Something went wrong!")
                    }
                }

            }
        }

    val googleSignInClient = GoogleSignIn.getClient(context, gso)

    // Column to hold all UI elements
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // Username text field
        OutlinedTextField(
            value = emailState.value,
            onValueChange = { emailState.value = it },
            label = { Text(stringResource(R.string.email)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Password text field
        OutlinedTextField(
            value = passwordState.value,
            onValueChange = { passwordState.value = it },
            label = { Text(stringResource(R.string.password)) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Login button
        Button(
            onClick = {
                if (emailState.value.isBlank() || passwordState.value.isBlank()) {
                    Toast.makeText(context, "Invalid email or password!", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                onLogin(emailState.value, passwordState.value)
            }, modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.login))
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Google sign-in button
        Button(
            onClick = { launcher.launch(googleSignInClient.signInIntent) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = stringResource(R.string.sign_in_with_google)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onRegistrationClick, modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.padding(start = 8.dp), text = stringResource(R.string.register)
            )
        }
    }
}
