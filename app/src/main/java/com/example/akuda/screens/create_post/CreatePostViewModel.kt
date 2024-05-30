package com.example.akuda.screens.create_post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.akuda.model.posts.FirebasePostsRepository
import kotlinx.coroutines.launch

class CreatePostViewModel(
    private val postsRepository: FirebasePostsRepository
) : ViewModel() {

    fun createPost(title: String, city: String, contents: String, imageUri: String) {
        viewModelScope.launch {
            postsRepository.addPost(
                title = title,
                city = city,
                contents = contents,
                imageUri = imageUri
            )
        }
    }

}