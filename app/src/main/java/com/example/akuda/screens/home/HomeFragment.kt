package com.example.akuda.screens.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.akuda.R
import com.example.akuda.Repositories
import com.example.akuda.databinding.FragmentHomeBinding
import com.example.akuda.screens.post_details.PostDetailsFragment
import com.example.akuda.screens.posts.PostsAdapter
import com.example.akuda.screens.posts.PostsListener

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val homeViewModel = HomeViewModel(Repositories.firebasePostsRepository)

    private val postsAdapter: PostsAdapter = PostsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupHomePostsRecyclerView()
        val adapter = PostsAdapter(
            object : PostsListener {
                override fun onPostClick(postId: String) {
                    val navController = activity?.findNavController(R.id.mainGraphContainer)
                    navController?.navigate(R.id.postDetailsFragment, bundleOf(PostDetailsFragment.POST_ID to postId))
                }

            }
        )
        val layoutManager = LinearLayoutManager(requireContext().applicationContext)

        binding.homePostsContainer.apply {
            this.adapter = adapter
            this.layoutManager = layoutManager
        }

        binding.searchPostsEditText.setOnEditorActionListener { searchView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                homeViewModel.fetchPostsByCity(searchView.text.toString())
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        homeViewModel.posts.observe(viewLifecycleOwner) { posts ->
            //Log.d("RRRR", it.toString())
            posts?.let { postsAdapter.posts = it }
        }

        return binding.root
    }

    private fun setupHomePostsRecyclerView() {
        val layoutManager = LinearLayoutManager(context)

        binding.homePostsContainer.apply {
            this.adapter = postsAdapter
            this.layoutManager = layoutManager
        }
    }

}