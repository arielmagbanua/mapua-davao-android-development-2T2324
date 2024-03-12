package com.example.clickracer.game.ui

import androidx.lifecycle.ViewModel
import com.example.clickracer.game.data.datastates.SessionsState
import com.example.clickracer.game.data.models.RaceSession
import com.example.clickracer.game.domain.usecases.CreateGameSession
import com.example.clickracer.game.domain.usecases.CurrentOpenSession
import com.example.clickracer.game.domain.usecases.ReadOpenSessions
import com.example.clickracer.game.domain.usecases.UpdateSession
import com.google.firebase.firestore.DocumentReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SessionsViewModel @Inject constructor(
    private val gameSession: CreateGameSession,
    private val readOpenSessions: ReadOpenSessions,
    private val currentOpenSession: CurrentOpenSession,
    private val updateSession: UpdateSession
) : ViewModel() {
    private val _uiState = MutableStateFlow(SessionsState())
    val uiState: StateFlow<SessionsState> = _uiState.asStateFlow()

    init {
        // read the open sessions
        readOpenSessions { updatedSessions ->
            // update sessions
            updateOpenSessions(updatedSessions)
        }
    }

    fun createGameSession(
        title: String,
        host: String,
        maxProgress: Int = 100,
        onAddSuccess: (doc: DocumentReference?) -> Unit,
    ) {
        gameSession(
            title = title,
            host = host,
            maxProgress = maxProgress,
            onAddSuccess = onAddSuccess
        )
    }

    private fun updateOpenSessions(updatedSessions: List<RaceSession>) {
        _uiState.update { currentState ->
            currentState.copy(
                openSessions = updatedSessions
            )
        }
    }

    fun readCurrentOpenSession(id: String, onSnapshot: (RaceSession) -> Unit) {
        currentOpenSession(id = id, onSnapshot = onSnapshot)
    }

    fun updateRaceSession(id: String, updatedSession: RaceSession) {
        updateSession(id = id, updatedSession = updatedSession)
    }
}
