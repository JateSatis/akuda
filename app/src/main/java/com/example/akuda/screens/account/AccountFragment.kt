package com.example.akuda.screens.account

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Nickname
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.akuda.databinding.FragmentAccountBinding

class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(inflater, container, false)

        return binding.root
    }


    private fun openGalleryForAccountPhoto() { //TODO: вызвать метод в слушателе кнопки обновления фото профиля
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    private fun updateNickname(nickname: String) {
        //TODO: обновление имени
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val imageUri = data.data
            //TODO: вызвать метод updateAccountPhoto с параметром imgaeUri на вьюмодели
        }
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }
}