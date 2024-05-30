package com.example.akuda.screens.account

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.akuda.R
import com.example.akuda.Repositories
import com.example.akuda.databinding.FragmentAccountBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding
    private lateinit var pagerAdapter: PostsPagerAdapter

    private var accountViewModel = AccountViewModel(
        Repositories.firebaseAccountRepository,
        Repositories.firebaseAuthRepository
    )

    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountBinding.inflate(inflater, container, false)

        Glide.with(this)
            .load(R.mipmap.account_default)
            .circleCrop()
            .into(binding.accountImage)

        accountViewModel.fetchAccountInfo()

        accountViewModel.account.observe(viewLifecycleOwner) {
            binding.accountEditNickname.setText(it?.nickname ?: "Максим Данилов")
            binding.accountEmailText.text = it?.email ?: "JateSatis@gmail.com"
            Glide.with(this)
                .load(Uri.parse(it?.photo))
                .circleCrop()
                .into(binding.accountImage)
        }
        pagerAdapter = PostsPagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter

        TabLayoutMediator(
            binding.tabLayout, binding.viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            when (position) {
                0 -> tab.text = "Мои публикации"
                1 -> tab.text = "Понравившиеся публикации"
            }
        }.attach()

        binding.accountImage.setOnClickListener {
            openGalleryForAccountPhoto()
        }

        binding.accountSaveChangesButton.setOnClickListener {
            if (imageUri != null) accountViewModel.updateAccountPhoto(imageUri!!)
            accountViewModel.updateAccountNickname(binding.accountEditNickname.text.toString())
        }

        binding.logOutButton.setOnClickListener {
            accountViewModel.signOut()
            val topNavController = activity?.findNavController(R.id.mainGraphContainer)
            topNavController?.navigate(R.id.signInFragment)
        }

        return binding.root
    }


    private fun openGalleryForAccountPhoto() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val imageUri = data.data
            this@AccountFragment.imageUri = imageUri!!
            Glide.with(this@AccountFragment)
                .load(imageUri)
                .circleCrop()
                .into(binding.accountImage)
        }
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }
}