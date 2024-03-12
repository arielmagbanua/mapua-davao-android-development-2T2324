package com.example.clickracer.game.domain.usecases

import com.example.clickracer.game.data.models.RaceSession
import com.example.clickracer.game.domain.repositories.SessionsRepository
import javax.inject.Inject

class UpdateSession @Inject constructor(private val sessionsRepository: SessionsRepository) {
    operator fun invoke(id: String, updatedSession: RaceSession, onUpdateSuccess: (() -> Unit)? = null) {
        sessionsRepository.updateSession(
            id = id,
            updatedSession = updatedSession,
            onUpdateSuccess = onUpdateSuccess
        )
    }
}
