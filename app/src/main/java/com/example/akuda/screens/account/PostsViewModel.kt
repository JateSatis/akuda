package com.example.akuda.screens.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.akuda.model.posts.FirebasePostsRepository
import com.example.akuda.model.posts.Post
import kotlinx.coroutines.launch

class PostsViewModel(
    private val postsRepository: FirebasePostsRepository,
    private val type: Int
) : ViewModel() {

    init {
        loadPosts(type)
    }

    private val _posts : MutableLiveData<List<Post>?> = MutableLiveData()
    val posts: LiveData<List<Post>?>
        get() = _posts

    fun loadPosts(type: Int) {
        viewModelScope.launch {
            val result = when(type) {
                PostsFragment.TYPE_MY_POSTS -> postsRepository.fetchMyPosts()
                PostsFragment.TYPE_LIKED_POSTS -> postsRepository.fetchLikedPosts()
                else -> emptyList()
            }
            _posts.postValue(result)
        }
    }
}