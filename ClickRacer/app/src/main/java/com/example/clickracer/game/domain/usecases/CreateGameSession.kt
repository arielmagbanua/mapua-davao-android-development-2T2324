package com.example.clickracer.game.domain.usecases

import com.example.clickracer.game.domain.repositories.SessionsRepository
import com.google.firebase.firestore.DocumentReference
import javax.inject.Inject

class CreateGameSession @Inject constructor(private val sessionsRepository: SessionsRepository) {
    operator fun invoke(
        title: String,
        host: String,
        maxProgress: Int = 100,
        onAddSuccess: (doc: DocumentReference?) -> Unit,
        onFailure: ((e: Exception) -> Unit)? = null
    ) {
        sessionsRepository.createGameSession(
            title = title,
            host = host,
            maxProgress = maxProgress,
            onAddSuccess = onAddSuccess,
            onFailure = onFailure
        )
    }
}
