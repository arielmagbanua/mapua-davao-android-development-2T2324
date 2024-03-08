package com.example.clickracer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clickracer.auth.ui.AuthViewModel
import com.example.clickracer.auth.ui.LoginScreen
import com.example.clickracer.ui.theme.ClickRacerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClickRacerTheme {
                App()
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

    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = if (authUiState.currentUser == null) "login" else "sessions"
    ) {
        composable(route = "login") {
            LoginScreen(
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
    }
}
