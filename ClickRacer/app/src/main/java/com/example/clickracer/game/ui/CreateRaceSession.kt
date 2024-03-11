package com.example.clickracer.game.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.clickracer.auth.ui.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRaceSession(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = viewModel(),
    sessionsViewModel: SessionsViewModel = viewModel(),
    onCreate: (id: String) -> Unit
) {
    val authUiState by authViewModel.uiState.collectAsState()

    val titleState = rememberSaveable { mutableStateOf("") }
    val maxProgressState = rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Create Race Session")
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout")
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = modifier.padding(innerPadding)
            ) {
                Column(
                    modifier = modifier
                        .padding(start = 16.dp, end = 16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = titleState.value,
                        onValueChange = { titleState.value = it },
                        label = { Text("Title") },
                        modifier = modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = maxProgressState.value,
                        onValueChange = { maxProgressState.value = it },
                        label = { Text("Max Progress") },
                        modifier = modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        )
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val currentEmail = authUiState.currentUser?.email

                if (currentEmail != null) {
                    // create the game session
                    sessionsViewModel.createGameSession(
                        title = titleState.value,
                        host = currentEmail,
                        maxProgress = Integer.valueOf(maxProgressState.value),
                        onAddSuccess = { sessionDoc ->
                            if (sessionDoc != null) {
                                onCreate(sessionDoc.id)
                            }
                        }
                    )
                }
            }) {
                Icon(Icons.Default.Save, contentDescription = "Save")
            }
        }
    )
}
