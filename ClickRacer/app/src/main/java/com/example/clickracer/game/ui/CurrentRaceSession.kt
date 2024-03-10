package com.example.clickracer.game.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.clickracer.auth.ui.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentRaceSession(
    id: String,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = viewModel(),
    sessionsViewModel: SessionsViewModel = viewModel(),
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Current Race Session")
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
                    modifier = Modifier.fillMaxSize()
                ) {
                    val authUiState by authViewModel.uiState.collectAsState()
                    val sessionsUiState by sessionsViewModel.uiState.collectAsState()

                    val titleState = rememberSaveable { mutableStateOf("") }
                    val hostState = rememberSaveable { mutableStateOf("") }
                    val maxProgressState = rememberSaveable { mutableStateOf("") }
                    val playersState = rememberSaveable { mutableStateOf(emptyList<HashMap<String, Any>>()) }
                    val startState = rememberSaveable { mutableStateOf(false) }

                    sessionsViewModel.readCurrentOpenSession(id) {
                        raceSession ->
                        titleState.value = raceSession.title
                        hostState.value = raceSession.host
                        maxProgressState.value = raceSession.maxProgress.toString()

                        // update list of players
                        playersState.value = raceSession.players
                    }

                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Title: " + titleState.value, style = MaterialTheme.typography.titleLarge)
                        Text(text = "Host: " + hostState.value, style = MaterialTheme.typography.titleLarge)
                        Text(text = "Max Progress: " + maxProgressState.value, style = MaterialTheme.typography.titleLarge)
                    }

                    // list of players
                    Spacer(modifier = Modifier.height(16.dp))

                    LazyColumn {
                        items(
                            items = playersState.value,
                            key = { player -> player["player"].toString()
                            }
                        ) { player ->
                            val playerName = player["player"].toString()
                            val progress = player["progress"].toString()

                            Text(modifier = Modifier.fillMaxWidth(), text = "$playerName -- $progress")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // controls
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (!startState.value) {
                            Button(modifier = Modifier.fillMaxWidth(), onClick = { startState.value = true }) {
                                Text(text = "Start")
                            }
                        }

                        if (startState.value) {
                            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                                // current user email
                                val email = authUiState.currentUser?.email
                                
                            }) {
                                Text(text = "Increment")
                            }
                        }
                    }
                }
            }
        }
    )
}
