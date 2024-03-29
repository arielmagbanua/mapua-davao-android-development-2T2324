package com.magbanua.todo.tasks.ui

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.magbanua.todo.tasks.data.MyTask
import com.magbanua.todo.tasks.data.TasksState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.tasks.await

class TasksViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TasksState())
    val uiState: StateFlow<TasksState> = _uiState.asStateFlow()

    fun updateCurrentTasks(tasks: List<MyTask>) {
        _uiState.update { currentState ->
            currentState.copy(
                tasks = tasks
            )
        }
    }

    fun readTasks(email: String) {
        val db = Firebase.firestore

        db.collection("tasks")
            .whereEqualTo("email", email)
            .addSnapshotListener { snapshot, e ->
                if (snapshot != null) {
                    val taskDocs = mutableListOf<MyTask>()

                    for (taskDoc in snapshot.documents) {
                        // convert each task document to task object
                        val task = (taskDoc as QueryDocumentSnapshot).toMyTask()
                        taskDocs.add(task)
                    }

                    updateCurrentTasks(taskDocs)
                }
            }
    }

    fun saveTask(
        title: String,
        description: String,
        email: String,
        onAddSuccess: (doc: DocumentReference?) -> Unit,
        onFailure: ((e: Exception) -> Unit)? = null
    ) {
        val db = Firebase.firestore

        // create document map
        val taskDocument = hashMapOf(
            "title" to title,
            "description" to description,
            "email" to email
        )

        // write to firestore
        db.collection("tasks").add(taskDocument)
            .addOnSuccessListener {
                onAddSuccess(it)
            }
            .addOnFailureListener {
                if (onFailure != null) {
                    onFailure(it)
                }
            }
    }

    fun updateTask(
        id: String,
        title: String,
        description: String,
        onUpdateSuccess: () -> Unit,
        onFailure: ((e: Exception) -> Unit)? = null
    ) {
        val db = Firebase.firestore

        // document update
        val taskDocument = hashMapOf(
            "title" to title,
            "description" to description
        ).toMap()

        db.collection("tasks")
            .document(id)
            .update(taskDocument)
            .addOnSuccessListener { onUpdateSuccess() }
    }

    fun deleteTask(id: String) {
        val db = Firebase.firestore
        db.collection("tasks").document(id).delete()
    }

    suspend fun readTask(id: String): MyTask {
        val db = Firebase.firestore
        val taskDoc = db.collection("tasks").document(id).get().await()
        return taskDoc.toMyTask()
    }
}

fun QueryDocumentSnapshot.toMyTask(): MyTask {
    return MyTask(
        id = this.id,
        title = this.getString("title"),
        description = this.getString("description"),
        email = this.getString("email")
    )
}

fun DocumentSnapshot.toMyTask(): MyTask {
    return MyTask(
        id = this.id,
        title = this.getString("title"),
        description = this.getString("description"),
        email = this.getString("email")
    )
}
