package com.example.akuda.screens.create_post

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.akuda.Repositories
import com.example.akuda.databinding.FragmentCreatePostBinding

class CreatePostFragment : Fragment() {

    private lateinit var binding: FragmentCreatePostBinding

    private val viewModel = CreatePostViewModel(Repositories.firebasePostsRepository)

    private var imageUri = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreatePostBinding.inflate(inflater, container, false)

        binding.postDetailsEditPhotoButton.setOnClickListener {
            openGalleryForAccountPhoto()
        }

        binding.createPostButton.setOnClickListener {
            val title = binding.postDetailsTitleInput.text.toString()
            val city = binding.postDetailsCityInput.text.toString()
            val contents = binding.postDetailsContentsInput.text.toString()

            if (title.isNotBlank() && city.isNotBlank() && contents.isNotBlank() && imageUri.isNotBlank()) {
                viewModel.createPost(title, city, contents, imageUri)
            } else {
                Toast.makeText(activity, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun openGalleryForAccountPhoto() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_POST_IMAGE_REQUEST)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_POST_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val imageUri = data.data
            Glide.with(this@CreatePostFragment)
                .load(imageUri)
                .into(binding.postDetailsPhoto)
            this@CreatePostFragment.imageUri = imageUri.toString()
        }
    }

    companion object {
        private const val PICK_POST_IMAGE_REQUEST = 1
    }

}