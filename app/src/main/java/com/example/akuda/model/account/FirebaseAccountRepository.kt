package com.example.akuda.model.account

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseAccountRepository {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser!!.uid

    suspend fun fetchAccountInfo() : Account? {
        return try {
            val documentSnapshot = db.collection("users").document(userId).get().await()
            if (documentSnapshot.exists()) {
                documentSnapshot.toObject(Account::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun saveAccountPhoto(photo: String): Boolean {
        return try {
            db.collection("users").document(userId).update("photo", photo).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun saveAccountNickname(nickname: String) : Boolean {
        return try {
            db.collection("users").document(userId).update("nickname", nickname).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}