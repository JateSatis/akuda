package com.example.akuda.model.posts

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebasePostsRepository {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser!!.uid

    suspend fun fetchAllPosts() : List<Post>? {
        return try {
            val postsSnapshot = db.collection("posts").get().await()
            if (!postsSnapshot.isEmpty) {
                postsSnapshot.map {
                    val postFirestore = it.toObject(PostFirestore::class.java)
                    return@map Post(id = it.id, data = postFirestore)
                }
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
            it.data.city == city
        }
    }

    suspend fun fetchMyPosts() : List<Post>? {
        val posts = fetchAllPosts()
        return posts?.filter {
            it.data.author == userId
        }
    }

    suspend fun fetchLikedPosts() : List<Post>? {
        val posts = fetchAllPosts()
        return posts?.filter {
            it.data.liked.contains(userId)
        }
    }

    suspend fun addPost(city: String, title: String, contents: String, imageUri: String) {
        val postFirestore = PostFirestore(
            city = city,
            title = title,
            contents = contents,
            author = userId,
            liked = emptyList(),
            rating = emptyList(),
            image = imageUri
        )
        try {
            db.collection("posts").add(postFirestore).await()
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

    suspend fun ratePost(id: String, rating: Int) {
        try {
            db.collection("posts").document(id).update("rating", FieldValue.arrayUnion(rating)).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}