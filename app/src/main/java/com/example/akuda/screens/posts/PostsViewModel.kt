package com.example.akuda.screens.posts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.akuda.model.posts.FirebasePostsRepository
import com.example.akuda.model.posts.Post
import kotlinx.coroutines.launch

class PostsViewModel(
    private val postsRepository: FirebasePostsRepository
) : ViewModel() {

    private val _posts = MutableLiveData<List<Post>>()
    val posts = _posts

    fun updatePosts(postsType: String) {
        viewModelScope.launch {
            _posts.value = when(postsType) {
                PostsFragment.LIKED -> postsRepository.fetchLikedPosts()
                PostsFragment.MY -> postsRepository.fetchMyPosts()
                PostsFragment.ALL -> postsRepository.fetchAllPosts()
                else -> postsRepository.fetchAllPosts()
            }
        }
    }

}