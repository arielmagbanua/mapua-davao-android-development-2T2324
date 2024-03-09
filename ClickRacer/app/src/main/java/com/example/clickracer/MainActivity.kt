package com.example.clickracer

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clickracer.auth.ui.AuthViewModel
import com.example.clickracer.auth.ui.LoginScreen
import com.example.clickracer.auth.ui.RegistrationScreen
import com.example.clickracer.game.ui.Sessions
import com.example.clickracer.ui.theme.ClickRacerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClickRacerTheme {
                App(
                    authViewModel = authViewModel
                )
            }
        }
    }
}

@Composable
fun App(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val authUiState by authViewModel.uiState.collectAsState()

    val context = LocalContext.current

    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = if (authUiState.currentUser == null) "login" else "races"
    ) {
        composable(route = "login") {
            LoginScreen(
                authViewModel = authViewModel,
                context = context,
                onLogin = { email, password ->
                    authViewModel.signIn(email, password)
                },
                onGoogleSignIn = { currentUser ->
                    if (currentUser != null) {
                        authViewModel.setCurrentUser(currentUser)
                    }
                },
                onRegistrationClick = {
                    // navigate to registration screen
                    navController.navigate("register")
                }
            )
        }

        composable(route = "register") {
            RegistrationScreen(
                onRegister = { email, password ->
                    // register the user
                    authViewModel.register(email = email, password = password) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.you_have_successfully_registered_enjoy_the_game),
                                Toast.LENGTH_SHORT
                            ).show()

                            // user was registered successfully so navigate back up
                            navController.navigateUp()
                        } else {
                            // failed
                            Toast.makeText(
                                context,
                                context.getString(R.string.something_went_wrong_please_try_to_register_again),
                                Toast.LENGTH_SHORT
                            ).show()

                            Log.e(
                                "SIGN_UP",
                                task.exception?.message
                                    ?: context.getString(R.string.something_went_wrong_please_try_to_register_again)
                            )
                        }
                    }
                },
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }

        composable(route = "races") {
            Sessions(authViewModel = authViewModel)
        }
    }
}
