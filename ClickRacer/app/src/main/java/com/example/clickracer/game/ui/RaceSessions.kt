package com.example.clickracer.game.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.clickracer.auth.ui.AuthViewModel
import com.example.clickracer.game.data.models.RaceSession

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RaceSessions(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = viewModel(),
    sessionsViewModel: SessionsViewModel = viewModel(),
    createRaceSession: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Races")
                },
                actions = {
                    IconButton(onClick = { authViewModel.logout() }) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout")
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(modifier.padding(innerPadding)) {
                val sessionsState by sessionsViewModel.uiState.collectAsState()

                RaceSessions(
                    sessions = sessionsState.openSessions,
                    onJoin = {
                        // TODO: join the game
                    }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = createRaceSession) {
                Icon(Icons.Default.Add, contentDescription = "Create a race")
            }
        }
    )
}

@Composable
fun RaceSessions(
    sessions: List<RaceSession>,
    onJoin: (id: String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(
            items = sessions,
            key = { session -> session.id }
        ) { session ->
            RaceSessionItem(session, onJoin)
        }
    }
}

@Composable
fun RaceSessionItem(raceSession: RaceSession, onJoin: (id: String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = raceSession.title, style = MaterialTheme.typography.titleLarge)
                Text(
                    text = raceSession.maxProgress.toString(),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Button(onClick = { onJoin(raceSession.id) }) {
                Text(text = "Join")
            }
        }
    }
}
