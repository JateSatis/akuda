package com.example.akuda.model.account

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseAccountRepository {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    suspend fun fetchAccountInfo() : Account? {
        return try {
            val documentSnapshot = db.collection("users").document(userId!!).get().await()
            if (documentSnapshot.exists()) {
                val account = documentSnapshot.toObject(Account::class.java)
                account
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getCurrentUserId() = userId

    suspend fun saveAccountPhoto(photo: String): Boolean {
        return try {
            db.collection("users").document(userId!!).update("photo", photo).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun saveAccountNickname(nickname: String) : Boolean {
        return try {
            db.collection("users").document(userId!!).update("nickname", nickname).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

}