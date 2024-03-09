package com.example.clickracer.game.data.models

data class Session(
    val id: String, // document id of the game session
    val title: String,
    val host: String, // the email of the game session host
    val maxProgress: Int = 100,
    val players: List<HashMap<String, Any>> = emptyList()
)
