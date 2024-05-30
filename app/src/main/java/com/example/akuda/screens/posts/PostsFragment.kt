package com.example.akuda.screens.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.akuda.Repositories
import com.example.akuda.databinding.FragmentPostsBinding

class PostsFragment : Fragment() {

    private lateinit var binding: FragmentPostsBinding

    private val viewModel = PostsViewModel(Repositories.firebasePostsRepository)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostsBinding.inflate(inflater, container, false)

        val postsType = arguments?.getString("TYPE")!!

        viewModel.updatePosts(postsType)

        val adapter = PostsAdapter()
        val layoutManager = LinearLayoutManager(requireContext().applicationContext)

        binding.postsRecycler.apply {
            this.adapter = adapter
            this.layoutManager = layoutManager
        }

        viewModel.posts.observe(viewLifecycleOwner) {
            adapter.posts = it
        }

        return binding.root
    }

    companion object {

        const val LIKED = "LIKED"
        const val MY = "MY"
        const val ALL = "ALL"

        fun newInstance(postsType: String): PostsFragment {
            val args = bundleOf("TYPE" to postsType)
            val fragment = PostsFragment()
            fragment.arguments = args
            return fragment
        }
    }

}