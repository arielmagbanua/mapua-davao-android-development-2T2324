package com.example.clickracer.game.domain.repositories

import com.example.clickracer.game.data.models.RaceSession
import com.google.firebase.firestore.DocumentReference

interface SessionsRepository {
    fun createGameSession(
        title: String,
        host: String,
        maxProgress: Int = 100,
        onAddSuccess: (doc: DocumentReference?) -> Unit,
        onFailure: ((e: Exception) -> Unit)? = null
    )

    fun readOpenSessions(onSessionsUpdate: (List<RaceSession>) -> Unit)
    fun readOpenSession(id: String, onSnapshot: (RaceSession) -> Unit)
}
