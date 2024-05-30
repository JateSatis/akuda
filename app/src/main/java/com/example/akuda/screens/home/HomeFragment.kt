package com.example.akuda.screens.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.akuda.R
import com.example.akuda.Repositories
import com.example.akuda.databinding.FragmentHomeBinding
import com.example.akuda.screens.posts.PostsAdapter

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val homeViewModel = HomeViewModel(Repositories.firebasePostsRepository)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val adapter = PostsAdapter()
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

        homeViewModel.posts.observe(viewLifecycleOwner) {
            adapter.posts = it
        }

        return binding.root
    }

}