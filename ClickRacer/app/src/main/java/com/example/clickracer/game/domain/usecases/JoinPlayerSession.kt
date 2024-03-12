package com.example.clickracer.game.domain.usecases

import com.example.clickracer.game.domain.repositories.SessionsRepository
import javax.inject.Inject

class JoinPlayerSession  @Inject constructor(private val sessionsRepository: SessionsRepository) {
    suspend operator fun invoke(id: String, email: String) {
        sessionsRepository.joinPlayer(id = id, email = email)
    }
}
