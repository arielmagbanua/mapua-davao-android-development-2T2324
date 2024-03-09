package com.example.clickracer.game.domain.usecases

import com.example.clickracer.game.data.models.RaceSession
import com.example.clickracer.game.domain.repositories.SessionsRepository
import javax.inject.Inject

class ReadOpenSessions @Inject constructor(private val sessionsRepository: SessionsRepository) {
    operator fun invoke(onSessionsUpdate: (List<RaceSession>) -> Unit) {
        sessionsRepository.readOpenSessions { updatedSessions ->
            onSessionsUpdate(updatedSessions)
        }
    }
}
