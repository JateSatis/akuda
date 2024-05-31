package com.example.akuda.screens.post_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.akuda.Repositories
import com.example.akuda.model.posts.FirebasePostsRepository
import com.example.akuda.model.posts.Post
import kotlinx.coroutines.launch

class PostDetailsViewModel(
    private val postsRepository: FirebasePostsRepository,
    private val postId: String
) : ViewModel() {

    private val _postDetails = MutableLiveData<Post>()
    val postDetails = _postDetails

    private val _isLikedByMe = MutableLiveData<Boolean>()
    val isLikedByMe = _isLikedByMe

    init {
        viewModelScope.launch {
            val postDetails = postsRepository.fetchPostById(postId)!!
            _postDetails.value = postDetails
            val userId = Repositories.firebaseAccountRepository.getCurrentUserId()
            _isLikedByMe.value = postDetails.liked.contains(userId)
        }
    }

    fun likePost(isLikedByMe: Boolean) {
        viewModelScope.launch {
            if (isLikedByMe) postsRepository.dislikePost(postId) else postsRepository.likePost(postId)
            _isLikedByMe.value = !isLikedByMe
        }
    }

}