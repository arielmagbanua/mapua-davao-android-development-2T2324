package com.magbanua.todo.tasks.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.magbanua.todo.auth.ui.AuthViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.magbanua.todo.tasks.data.MyTask

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = viewModel(),
    tasksViewModel: TasksViewModel = viewModel(),
    onLogout: () -> Unit,
    onAddTask: () -> Unit,
    onEdit: (String?) -> Unit
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
            // get the state of the tasks and use it to populate the tasks
            val tasksUiState by tasksViewModel.uiState.collectAsState()

            // get the auth state to grab the current email of the user
            val authUiState by authViewModel.uiState.collectAsState()
            val currentEmail = authUiState.currentUser?.email

            if (currentEmail != null) {
                // read the tasks or update the tasks in case there is new task added
                tasksViewModel.readTasks(email = currentEmail) { tasks ->
                    tasksViewModel.updateCurrentTasks(tasks)
                }
            }

            Column(modifier.padding(innerPadding)) {
                TaskList(
                    tasks = tasksUiState.tasks,
                    onDelete = { id ->
                        if (id != null) {
                            tasksViewModel.deleteTask(id)
                        }
                    },
                    onEdit = onEdit
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
fun TaskList(
    tasks: List<MyTask> = emptyList(),
    onDelete: (String?) -> Unit,
    onEdit: (String?) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(
            items = tasks,
            key = { task ->
                task.id ?: ""
            } // provide the key that will be used to track items in the list
        ) { task ->
            TaskCard(
                id = task.id,
                title = task.title ?: "",
                description = task.description ?: "",
                onDelete = onDelete,
                onEdit = onEdit
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCard(
    id: String?,
    title: String,
    description: String,
    onDelete: (String?) -> Unit,
    onEdit: (String?) -> Unit
) {
    SwipeToDismiss(
        state = rememberDismissState(
            confirmValueChange = {
                onDelete(id) // execute delete after swipe left
                true
            }
        ),
        background = {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = "Delete",
                    tint = Color.Red
                )
            }
        },
        dismissContent = {
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
                        IconButton(onClick = { onEdit(id) }) {
                            Icon(
                                Icons.Filled.Edit,
                                contentDescription = "Edit"
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
        },
    )
}
