package com.magbanua.todo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.magbanua.todo.auth.ui.AuthViewModel
import com.magbanua.todo.auth.ui.LoginScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun TodoApp(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LoginScreen(
            onLogin = {username, password ->},
            onGoogleSignIn = {
                    currentUser ->
                if (currentUser != null) {
                    viewModel.setCurrentUser(currentUser)
                }
            }
        )
    }
}
