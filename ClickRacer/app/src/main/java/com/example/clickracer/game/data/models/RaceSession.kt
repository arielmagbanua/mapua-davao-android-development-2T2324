package com.example.clickracer.game.data.models

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot

data class RaceSession(
    val id: String, // document id of the game session
    val title: String,
    val host: String, // the email of the game session host
    val maxProgress: Long = 100,
    val hasStarted: Boolean = false,
    val players: HashMap<String, Long> = hashMapOf()
)

fun QueryDocumentSnapshot.toRaceSession(): RaceSession {
    val data = this.get("players")

    return RaceSession(
        id = this.id,
        title = this.getString("title") ?: "",
        host = this.getString("host") ?: "",
        maxProgress = this.getLong("maxProgress") ?: 100,
        hasStarted = this.getBoolean("hasStarted") ?: false,
        players = data as HashMap<String, Long>
    )
}

fun DocumentSnapshot.toRaceSession(): RaceSession {
    val data = this.get("players")

    return RaceSession(
        id = this.id,
        title = this.getString("title") ?: "",
        host = this.getString("host") ?: "",
        maxProgress = this.getLong("maxProgress") ?: 100,
        hasStarted = this.getBoolean("hasStarted") ?: false,
        players = data as HashMap<String, Long>
    )
}
