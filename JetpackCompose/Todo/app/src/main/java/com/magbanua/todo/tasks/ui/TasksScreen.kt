package com.magbanua.todo.tasks.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.magbanua.todo.auth.ui.AuthViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.magbanua.todo.tasks.data.MyTask

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = viewModel(),
    onLogout: () -> Unit,
    onAddTask: () -> Unit
) {
    Scaffold (
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Tasks")
                },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout")
                    }
                }
            )
        },
        content = { innerPadding ->
            val tasksState = remember { mutableStateOf<MutableList<MyTask>>(emptyList<MyTask>().toMutableList()) }

            viewModel.readTasks { tasks ->
                if (tasks != null) {
                    tasksState.value = tasks
                }
            }

            Column (modifier.padding(innerPadding)) {
                // val tasks = listOf<String>("Task 1", "Task 2", "Task 3")
                TaskList(tasksState.value)
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTask) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    )
}

@Composable
fun TaskList(tasks: List<MyTask> = emptyList()) {
    LazyColumn {
        items(tasks) { task ->
            // Replace this with your desired composable to display each document
            Text(text = task.title)
        }
    }
}
