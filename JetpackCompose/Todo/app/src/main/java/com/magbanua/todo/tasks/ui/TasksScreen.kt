package com.magbanua.todo.tasks.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.dp
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
    Scaffold(
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
            val tasksState = remember { mutableStateOf(emptyList<MyTask>()) }

            viewModel.readTasks { tasks ->
                tasksState.value = tasks
            }

            Column(modifier.padding(innerPadding)) {
                TaskList(
                    tasks = tasksState.value,
                    onDelete = { id ->

                    }
                )
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
fun TaskList(tasks: List<MyTask> = emptyList(), onDelete: (String?) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(tasks) { task ->
            TaskCard(
                id = task.id,
                title = task.title ?: "",
                description = task.description ?: "",
                onDelete = onDelete
            )
        }
    }
}

@Composable
fun TaskCard(
    id: String?,
    title: String,
    description: String,
    onDelete: (String?) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = title, style = MaterialTheme.typography.titleLarge)
                IconButton(onClick = { onDelete(id) }) {
                    Icon(
                        Icons.Filled.DeleteForever,
                        contentDescription = "Delete"
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
