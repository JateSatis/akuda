package com.example.akuda.screens.post_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.akuda.model.posts.FirebasePostsRepository
import com.example.akuda.model.posts.Post
import kotlinx.coroutines.launch

class PostDetailsViewModel(
    private val postsRepository: FirebasePostsRepository,
    private val postId: String
) : ViewModel() {

    private val _postDetails = MutableLiveData<Post>()
    val postDetails = _postDetails

    init {
        viewModelScope.launch {
            _postDetails.value = postsRepository.fetchPostById(postId)
        }
    }

}