package com.magbanua.todo.auth.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.magbanua.todo.auth.data.AuthState
import com.magbanua.todo.tasks.data.MyTask
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AuthViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(AuthState())
    val uiState: StateFlow<AuthState> = _uiState.asStateFlow()

    init {
        val currentUser = FirebaseAuth.getInstance().currentUser;
        setCurrentUser(currentUser)
    }

    fun setCurrentUser(user: FirebaseUser? = null) {
        // update the current user state
        _uiState.update { currentState -> currentState.copy(
            currentUser = user)
        }
    }

    fun logout() {
        // sign-out
        FirebaseAuth.getInstance().signOut()

        // set the current user to null
        setCurrentUser(null)
    }

    fun register(
        email: String,
        password: String,
        onRegistrationComplete: (task: Task<AuthResult>) -> Unit
    ) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task -> onRegistrationComplete(task)}
    }

    fun signIn(email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign-in successful
                    // set the current user
                    setCurrentUser(FirebaseAuth.getInstance().currentUser)
                } else {
                    // Sign-in failed
                    // Handle exception and display error message
                    Log.e("SIGN_IN", "Something went wrong!")
                }
            }
    }

    fun saveTask(
        title: String,
        description: String,
        onAddSuccess: (doc: DocumentReference?) -> Unit,
        onFailure: ((e: Exception) -> Unit)? = null) {
        val db = Firebase.firestore

        // create document map
        val taskDocument = hashMapOf(
            "title" to title,
            "description" to description,
            "email" to _uiState.value.currentUser?.email
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

    fun readTasks(onTasksUpdate: (tasks: MutableList<MyTask>?) -> Unit) {
        val db = Firebase.firestore
        val email = _uiState.value.currentUser?.email

        db.collection("tasks")
            .whereEqualTo("email", email)
            .addSnapshotListener{
                    snapshot, e ->

                val tasks = mutableListOf<MyTask>()

                if (snapshot != null) {
                    for (doc in snapshot) {
                        val title = doc.getString("title")
                        val description = doc.getString("description")

                        val myTask = MyTask(title = title ?: "", description = description ?: "")
                        tasks.add(myTask)
                    }
                }

                onTasksUpdate(tasks)
            }
    }
}
