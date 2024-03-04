package com.magbanua.todo

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.magbanua.todo.auth.ui.AuthViewModel
import com.magbanua.todo.auth.ui.LoginScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.magbanua.todo.auth.ui.RegistrationScreen
import com.magbanua.todo.tasks.ui.AddTaskScreen
import com.magbanua.todo.tasks.ui.TasksScreen

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
        // get the auth ui state
        val uiState by viewModel.uiState.collectAsState()

        // fetch the currently logged user

        // switch screen based on the current state of the user
        LaunchedEffect(uiState.currentUser) {
            if (uiState.currentUser != null) {
                // successful login
                navController.navigate("tasks")
            } else {
                // no more user then show the login screen
                navController.navigate("login")
            }
        }

        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = modifier
        ) {
            composable(route = "login") {
                LoginScreen(
                    onLogin = { email, password ->
                        viewModel.signIn(email, password)
                    },
                    onGoogleSignIn = {
                            currentUser ->
                        if (currentUser != null) {
                            viewModel.setCurrentUser(currentUser)
                        }
                    },
                    onRegistrationClick = {
                        // navigate to registration screen
                        navController.navigate("register")
                    }
                )
            }

            composable(route = "tasks") {
                TasksScreen(
                    onLogout = {
                        // sign-out firebase user
                        viewModel.logout()
                    },
                    onAddTask = {
                        // navigate to add task screen
                        navController.navigate("add_task")
                    }
                )
            }

            composable(route = "register") {
                RegistrationScreen(
                    onRegister = { email, password ->
                        // register the user
                        viewModel.register(email = email, password = password) { task ->
                            if (task.isSuccessful) {
                                // user was registered successfully so navigate back up
                                navController.navigateUp()
                            } else {
                                // failed
                                Log.e("SIGN_UP", "Something went wrong!")
                            }
                        }
                    },
                    navigateUp = {
                        navController.navigateUp()
                    }
                )
            }

            composable(route = "add_task") {
                AddTaskScreen(
                    navigateUp = {
                        navController.navigateUp()
                    },
                    saveTask = {
                        title, description ->

                        navController.navigateUp()

                        // write task to firestore
                        viewModel.saveTask(title = title, description = description, onAddSuccess = {
                            Log.d("SAVE_TASK", it.toString())
                        },
                            onFailure = {
                                Log.e("SAVE_TASK", it.message.toString())
                            })
                    }
                )
            }
        }
    }
}
