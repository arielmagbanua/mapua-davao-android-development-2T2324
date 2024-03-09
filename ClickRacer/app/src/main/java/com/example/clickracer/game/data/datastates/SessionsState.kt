package com.example.clickracer.game.data.datastates

import com.example.clickracer.game.data.models.RaceSession

data class SessionsState(
    val openSessions: List<RaceSession> = emptyList()
)
