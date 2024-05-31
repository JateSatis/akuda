package com.example.akuda.screens.post_details

import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.akuda.R
import com.example.akuda.Repositories
import com.example.akuda.databinding.FragmentPostDetailsBinding

class PostDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPostDetailsBinding
    private lateinit var postDetailsViewModel: PostDetailsViewModel
    private var isLikedByMe = true

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
                postDetailsAuthor.text = it.authorName
                postDetailsCity.text = it.city
                postDetailsRating.text = (it.rating.sum() / it.rating.size).toString()
                postDetailsContents.text = it.contents
            }

            Glide.with(this@PostDetailsFragment)
                .load(Uri.parse(it.image))
                .transform(RoundedCorners(20))
                .into(binding.postDetailsImage)
        }

        postDetailsViewModel.isLikedByMe.observe(viewLifecycleOwner) {
            updateLikeImageColor(it)
            isLikedByMe = it
        }

        binding.postDetailsGoBackButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.postDetailsLikeButton.setOnClickListener {
            updateLikeImageColor(!isLikedByMe)
            postDetailsViewModel.likePost(isLikedByMe)
        }

        return binding.root;
    }

    private fun updateLikeImageColor(isLikedByMe: Boolean) {

        val color = if (isLikedByMe) R.color.orange else R.color.background_dark_gray

        val postDetailsImage = binding.postDetailsLikeButton
        val context = this@PostDetailsFragment.requireContext()

        val drawable = ContextCompat.getDrawable(context, R.drawable.ic_like)
        val colorCompat = ContextCompat.getColor(context, color)
        drawable?.setColorFilter(colorCompat, PorterDuff.Mode.SRC_IN)
        postDetailsImage.setImageDrawable(drawable)
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