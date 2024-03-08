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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.clickracer.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

@Composable
fun LoginScreen(
    context: Context,
    authViewModel: AuthViewModel = viewModel(),
    onLogin: (String, String) -> Unit,
    onGoogleSignIn: (currentUser: FirebaseUser?) -> Unit,
    onRegistrationClick: () -> Unit
) {
    // State variables for username and password
    val emailState = rememberSaveable { mutableStateOf("") }
    val passwordState = rememberSaveable { mutableStateOf("") }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            authViewModel.googleSign(result) { currentUser ->
                onGoogleSignIn(currentUser)
            }
        }
    }

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
            onClick = { launcher.launch(authViewModel.createGoogleSignInClient().signInIntent) },
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
