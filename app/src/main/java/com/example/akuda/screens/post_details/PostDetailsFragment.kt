package com.example.akuda.screens.post_details

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.akuda.Repositories
import com.example.akuda.databinding.FragmentPostDetailsBinding

class PostDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPostDetailsBinding
    private lateinit var postDetailsViewModel: PostDetailsViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostDetailsBinding.inflate(inflater, container, false)

        val postId = arguments?.getString(POST_ID)

        postDetailsViewModel = PostDetailsViewModel(Repositories.firebasePostsRepository, postId!!)

        postDetailsViewModel.postDetails.observe(viewLifecycleOwner) {
            binding.apply {
                postDetailsTitle.text = it.title
                postDetailsAuthor.text = it.author
                postDetailsCity.text = it.city
                postDetailsRating.text = (it.rating.sum() / it.rating.size).toString()
                postDetailsContents.text = it.contents
            }

            Log.d("POST_DETAILS", it.image)
            Glide.with(this@PostDetailsFragment)
                .load(Uri.parse(it.image))
                .transform(RoundedCorners(20))
                .into(binding.postDetailsImage)
        }

        return binding.root;
    }

    companion object {
        fun newInstance(postId: String) : PostDetailsFragment {
            val args = bundleOf(POST_ID to postId)
            val postDetailsFragment = PostDetailsFragment()
            postDetailsFragment.arguments = args
            return postDetailsFragment
        }

        const val POST_ID = "POST_ID"
    }
}