package com.magbanua.todo

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
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
import com.magbanua.todo.tasks.ui.TasksViewModel

@Composable
fun TodoApp(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = viewModel(),
    tasksViewModel: TasksViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // get the auth ui state
        val authUiState by authViewModel.uiState.collectAsState()
        val currentEmail = authUiState.currentUser?.email

        NavHost(
            navController = navController,
            startDestination = if (authUiState.currentUser == null) "login" else "tasks",
            modifier = modifier
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

            composable(route = "tasks") {
                TasksScreen(
                    onLogout = {
                        // sign-out firebase user
                        authViewModel.logout()
                    },
                    onAddTask = {
                        // navigate to add task screen
                        navController.navigate("add_task")
                    },
                    onEdit = {
                        navController.navigate("edit_task/$it")
                    }
                )
            }

            composable(route = "register") {
                RegistrationScreen(
                    onRegister = { email, password ->
                        // register the user
                        authViewModel.register(email = email, password = password) { task ->
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
                    navigateUp = { navController.navigateUp() },
                    saveTask = { title, description ->
                        navController.navigateUp()

                        if (currentEmail != null) {
                            // write task to firestore
                            tasksViewModel.saveTask(
                                title = title,
                                description = description,
                                email = currentEmail,
                                onAddSuccess = {
                                    Log.d("SAVE_TASK", it.toString())
                                },
                                onFailure = {
                                    Log.e("SAVE_TASK", it.message.toString())
                                },
                            )
                        }
                    }
                )
            }

            composable(route = "edit_task/{id}") { backStackEntry ->
                // get the id parameter
                val id = backStackEntry.arguments?.getString("id")

                AddTaskScreen(
                    id = id,
                    navigateUp = { navController.navigateUp() },
                    saveTask = { title, description ->
                        navController.navigateUp()

                        if (id != null) {
                            tasksViewModel.updateTask(
                                id = id,
                                title = title,
                                description = description,
                                onUpdateSuccess = {
                                    Log.d("UPDATE_TASK", "Updated success")
                                },
                                onFailure = {
                                    Log.e("UPDATE_TASK", it.message.toString())
                                },
                            )
                        }
                    }
                )
            }
        }
    }
}
