package com.magbanua.todo.tasks.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.magbanua.todo.tasks.data.MyTask
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    modifier: Modifier = Modifier,
    id: String? = null,
    tasksViewModel: TasksViewModel = viewModel(),
    navigateUp: () -> Unit,
    saveTask: (title: String, description: String) -> Unit
) {
    val taskToEdit = remember { mutableStateOf<MyTask?>(null) }
    val titleState = remember { mutableStateOf("") }
    val descriptionState = remember { mutableStateOf("") }

    // if id is passed then it should be edit mode and retrieve the title and description
    if (id != null) {
        LaunchedEffect(null) {
            val myTask = tasksViewModel.readTask(id)
            taskToEdit.value = myTask
        }

        if (taskToEdit.value != null) {
            titleState.value = taskToEdit.value?.title ?: ""
            descriptionState.value = taskToEdit.value?.description ?: ""
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Add Task")
                },
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "back"
                        )
                    }
                }
            )
        },
        content = {
            innerPadding ->
            Column (
                modifier = modifier
                .padding(innerPadding)
            ) {
                Column (modifier = modifier.padding(start = 16.dp, end = 16.dp)) {
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = titleState.value,
                        onValueChange = { titleState.value = it },
                        label = { Text("Title") },
                        modifier = modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = descriptionState.value,
                        onValueChange = { descriptionState.value = it },
                        label = { Text("Description") },
                        modifier = modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { saveTask(titleState.value, descriptionState.value) }) {
                Icon(Icons.Default.Save, contentDescription = "Save")
            }
        }
    )
}
