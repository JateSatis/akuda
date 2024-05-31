package com.example.akuda.model.posts

import android.util.Log
import com.example.akuda.Repositories
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

class FirebasePostsRepository {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    suspend fun fetchAllPosts() : List<Post>? {
        return try {
            val postsSnapshot = db.collection("posts").get().await()
            if (!postsSnapshot.isEmpty) {
                postsSnapshot.map {
                    val post = it.toObject(Post::class.java)
                    post.copy(id = it.id)
                }
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun fetchPostById(postId: String) : Post? {
        return try {
            val postSnapshot = db.collection("posts").document(postId).get().await()
            if (postSnapshot.exists()) {
                val post = postSnapshot.toObject(Post::class.java)
                post?.copy(id = postSnapshot.id)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun fetchPostsByCity(city: String) : List<Post>? {
        val posts = fetchAllPosts()
        return posts?.filter {
            it.city == city
        }
    }

    suspend fun fetchMyPosts() : List<Post>? {
        val posts = fetchAllPosts()
        return posts?.filter {
            it.author == userId
        }
    }

    suspend fun fetchLikedPosts() : List<Post>? {
        val posts = fetchAllPosts()
        return posts?.filter {
            it.liked.contains(userId)
        }
    }

    suspend fun addPost(city: String, title: String, contents: String, imageUri: String) {

        val accountInfo = Repositories.firebaseAccountRepository.fetchAccountInfo()

        val post = Post(
            city = city,
            title = title,
            contents = contents,
            author = userId!!,
            authorName = accountInfo?.nickname!!,
            liked = emptyList(),
            rating = emptyList(),
            image = imageUri
        )
        try {
            val documentReference = db.collection("posts").add(post).await()
            val documentId = documentReference.id
            db.collection("posts").document(documentId).update("id", documentId).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun likePost(id: String) {
        try {
            db.collection("posts").document(id).update("liked", FieldValue.arrayUnion(userId)).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    suspend fun dislikePost(id: String) {
        try {
            db.collection("posts").document(id).update("liked", FieldValue.arrayRemove(userId)).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    suspend fun ratePost(id: String, rating: Int) {
        try {
            db.collection("posts").document(id).update("rating", FieldValue.arrayUnion(rating)).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}