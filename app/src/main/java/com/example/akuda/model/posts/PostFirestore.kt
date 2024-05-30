package com.example.akuda.model.posts

data class PostFirestore(
    val author: String,
    val city: String,
    val contents: String,
    val image: String,
    val liked: List<String>,
    val rating: List<Double>,
    val title: String
)