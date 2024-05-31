package com.example.akuda.model.posts

data class Post (
    var id: String = "",
    val author: String = "",
    val authorName: String = "",
    val city: String = "",
    val contents: String ="",
    val image: String = "",
    val liked: List<String> = emptyList(),
    val rating: List<Double> = emptyList(),
    val title: String = ""
)
