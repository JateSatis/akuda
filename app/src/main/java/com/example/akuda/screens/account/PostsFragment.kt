package com.example.akuda.screens.account

import android.R.attr.type
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.akuda.Repositories
import com.example.akuda.databinding.FragmentPostsBinding
import com.example.akuda.screens.posts.PostsAdapter


class PostsFragment : Fragment() {

    private lateinit var binding: FragmentPostsBinding

    private lateinit var viewModel: PostsViewModel

    private val type: Int by lazy {
        arguments?.getInt(ARG_TYPE) ?: 0
    }

    private val postsAdapter: PostsAdapter = PostsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = PostsViewModel(Repositories.firebasePostsRepository, type)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostsBinding.inflate(inflater, container, false)

        observePosts()

        setupPostsRecyclerView()

        return binding.root
    }

    private fun observePosts() {
        viewModel.posts.observe(viewLifecycleOwner) { posts ->
            posts?.let { postsAdapter.posts = it.toMutableList() }
            //"toMutableList" сделан для поиска
        }
    }

    private fun setupPostsRecyclerView() {
        binding.postsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.postsRecyclerView.adapter = postsAdapter
    }

    companion object {

        const val TYPE_MY_POSTS = 0
        const val TYPE_LIKED_POSTS = 1

        private const val ARG_TYPE = "type"

        fun newInstance(postsType: Int) : PostsFragment {
            val fragment = PostsFragment()
            val args = Bundle()
            args.putInt(ARG_TYPE, postsType)
            fragment.arguments = args
            return fragment
        }
    }
}