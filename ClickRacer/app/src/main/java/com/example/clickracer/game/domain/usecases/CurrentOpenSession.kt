package com.example.clickracer.game.domain.usecases

import com.example.clickracer.game.data.models.RaceSession
import com.example.clickracer.game.domain.repositories.SessionsRepository
import javax.inject.Inject

class CurrentOpenSession @Inject constructor(private val sessionsRepository: SessionsRepository) {
    operator fun invoke(id: String, onSnapshot: (RaceSession) -> Unit) {
        sessionsRepository.readOpenSession(id = id, onSnapshot = onSnapshot)
    }
}
