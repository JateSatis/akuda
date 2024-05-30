package com.example.akuda.screens.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.akuda.model.posts.FirebasePostsRepository
import com.example.akuda.model.posts.Post
import com.example.akuda.screens.posts.PostsFragment
import kotlinx.coroutines.launch

class HomeViewModel(
    val postsRepository: FirebasePostsRepository
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

    fun fetchAllPosts() {
        viewModelScope.launch {
            _posts.value = postsRepository.fetchAllPosts()
        }
    }

    fun fetchPostsByCity(city: String) {
        viewModelScope.launch {
            _posts.value = postsRepository.fetchPostsByCity(city)
        }
    }

}