package com.example.akuda.screens.account

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.akuda.Repositories
import com.example.akuda.databinding.FragmentAccountBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding
    private lateinit var pagerAdapter: PostsPagerAdapter

    private var accountViewModel = AccountViewModel(Repositories.firebaseAccountRepository)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountBinding.inflate(inflater, container, false)

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

        binding.accountEditNickname.setOnEditorActionListener { nicknameView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                accountViewModel.updateAccountNickname(nicknameView.text.toString())
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        binding.accountImage.setOnClickListener {
            openGalleryForAccountPhoto()
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
            accountViewModel.updateAccountPhoto(imageUri!!)
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