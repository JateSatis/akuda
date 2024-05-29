package com.example.akuda.model.auth

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FirebaseAuthRepository {
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    suspend fun createUserWithEmailAndPassword(email: String, password: String, nickname: String) : AuthResult {
        val authResult = auth.createUserWithEmailAndPassword(email, password).await()
        val userID = authResult.user?.uid ?: throw IllegalStateException("User ID is null")
        createUserDocument(userID, email, nickname)
        return authResult
    }

    suspend fun signInWithEmailAndPassword(email: String, password: String): AuthResult {
        return auth.signInWithEmailAndPassword(email, password).await()
    }

    fun signOut() {
        auth.signOut()
    }

    private suspend fun createUserDocument(userID: String, email: String, nickname: String) {
        val userData = mapOf(
            "email" to email,
            "nickname" to nickname,
            "registrationDate" to getCurrentFormattedDate()
        )
        db.collection("users").document(userID).set(userData).await()
    }
    private fun getCurrentFormattedDate(): String {
        val calendar = Calendar.getInstance()
        val formatter = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
        return formatter.format(calendar.time)
    }
}