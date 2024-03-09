package com.example.clickracer.game.data.repositories

import com.example.clickracer.game.data.models.RaceSession
import com.example.clickracer.game.data.models.toRaceSession
import com.example.clickracer.game.domain.repositories.SessionsRepository
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SessionsRepositoryImplementation : SessionsRepository {
    private var db: FirebaseFirestore = Firebase.firestore

    override fun createGameSession(
        title: String,
        host: String,
        maxProgress: Int,
        onAddSuccess: (doc: DocumentReference?) -> Unit,
        onFailure: ((e: Exception) -> Unit)?
    ) {
        // create document map
        val sessionDocMap = hashMapOf(
            "title" to title,
            "host" to host,
            "maxProgress" to maxProgress,
            "hasStarted" to false
        )

        db.collection("game_sessions")
            .add(sessionDocMap)
            .addOnSuccessListener { task ->
                onAddSuccess(task)
            }
            .addOnFailureListener {
                if (onFailure != null) {
                    onFailure(it)
                }
            }
    }

    override fun readOpenSessions(onSessionsUpdate: (List<RaceSession>) -> Unit) {
        db.collection("game_sessions")
            .whereEqualTo("hasStarted", false)
            .addSnapshotListener { snapshot , e ->
                if (snapshot != null) {
                    val sessions = mutableListOf<RaceSession>()

                    for (sessionDoc in snapshot.documents) {
                        // convert each task document to task object
                        val session = (sessionDoc as QueryDocumentSnapshot).toRaceSession()
                        sessions.add(session)
                    }

                    onSessionsUpdate(sessions)
                }
            }
    }
}
