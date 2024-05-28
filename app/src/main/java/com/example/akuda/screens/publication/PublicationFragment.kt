package com.example.akuda.screens.publication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.akuda.databinding.FragmentPublicationBinding

class PublicationFragment : Fragment() {

    private lateinit var binding: FragmentPublicationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPublicationBinding.inflate(inflater, container, false)

        return binding.root;
    }

}