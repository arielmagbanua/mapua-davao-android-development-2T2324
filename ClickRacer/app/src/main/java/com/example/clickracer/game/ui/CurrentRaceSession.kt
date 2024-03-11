package com.example.clickracer.game.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.clickracer.auth.ui.AuthViewModel
import com.example.clickracer.ui.TextWithLabel

@SuppressLint("MutableCollectionMutableState")
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
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, end = 16.dp),
                ) {
                    val authUiState by authViewModel.uiState.collectAsState()
                    val sessionsUiState by sessionsViewModel.uiState.collectAsState()

                    val titleState = rememberSaveable { mutableStateOf("") }
                    val hostState = rememberSaveable { mutableStateOf("") }
                    val maxProgressState = rememberSaveable { mutableStateOf("0.0") }
                    val playersState =
                        rememberSaveable { mutableStateOf(hashMapOf<String, Long>()) }
                    val startState = rememberSaveable { mutableStateOf(false) }

                    sessionsViewModel.readCurrentOpenSession(id) { raceSession ->
                        titleState.value = raceSession.title
                        hostState.value = raceSession.host
                        maxProgressState.value = raceSession.maxProgress.toString()

                        // update list of players
                        playersState.value = raceSession.players
                    }

                    // game info section
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = titleState.value,
                            style = MaterialTheme.typography.headlineLarge,
                            textAlign = TextAlign.Center,
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        TextWithLabel(text = hostState.value, label = "Host")
                        Spacer(modifier = Modifier.height(8.dp))
                        TextWithLabel(text = maxProgressState.value, label = "Max Progress")
                    }

                    // player progress section
                    Spacer(modifier = Modifier.height(32.dp))
                    // convert to list to so that it can be loaded to Lazy Column
                    val playersList = playersState.value.map {
                        hashMapOf(it.key to it.value)
                    }.toList()
                    LazyColumn {
                        items(
                            items = playersList,
                            key = { player -> player.keys.first() }
                        ) { player ->
                            // the key is the player name
                            val playerName = player.keys.first()
                            val progress = player[playerName].toString().toLong()

                            PlayerProgressIndicator(
                                name = playerName,
                                progress = progress,
                                maxProgress = maxProgressState.value.toLong()
                            )
                        }
                    }

                    // controls
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // only allow starting of the game if the user is the host
                        // and if there are 2 or more players
                        // if (!startState.value && authUiState.currentUser?.email == hostState.value && playersState.value.count() > 1) {
                        if (!startState.value && authUiState.currentUser?.email == hostState.value) {
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = { startState.value = true }) {
                                Text(text = "Start")
                            }
                        }

                        if (startState.value) {
                            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                                val currentPlayer = authUiState.currentUser?.email
                                if (currentPlayer != null) {
                                    val currentPlayerProgress =
                                        (playersState.value[currentPlayer] ?: 0) + 1

                                    // update the players
                                    val updatePlayers = hashMapOf<String, Long>()
                                    val iterator = playersState.value.iterator()
                                    if (iterator.hasNext()) {
                                        val entry = iterator.next()
                                        val playerName = entry.key
                                        val playerProgress = entry.value
                                        updatePlayers[playerName] =
                                            if (playerName == currentPlayer) currentPlayerProgress else playerProgress
                                    }

                                    // TODO: update the game session

                                }
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

@Composable
fun PlayerProgressIndicator(name: String, progress: Long, maxProgress: Long) {
    // calculate the progress
    val currentProgress = (progress / maxProgress) * 100.0

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = name, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { currentProgress.toFloat() },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
