package com.example.akuda

import com.example.akuda.model.auth.FirebaseAuthRepository

object Repositories {
    val firebaseAuthRepository: FirebaseAuthRepository by lazy {
        FirebaseAuthRepository.getInstance()
    }
}