package com.example.akuda

import com.example.akuda.model.account.FirebaseAccountRepository
import com.example.akuda.model.auth.FirebaseAuthRepository
import com.example.akuda.model.posts.FirebasePostsRepository

object Repositories {
    val firebaseAuthRepository: FirebaseAuthRepository = FirebaseAuthRepository()
    val firebaseAccountRepository: FirebaseAccountRepository = FirebaseAccountRepository()
    val firebasePostsRepository: FirebasePostsRepository = FirebasePostsRepository()
}