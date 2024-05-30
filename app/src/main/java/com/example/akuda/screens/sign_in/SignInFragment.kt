package com.example.akuda.screens.sign_in

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.akuda.R
import com.example.akuda.Repositories
import com.example.akuda.databinding.FragmentSignInBinding
import com.example.akuda.screens.sign_up.OperationState

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding

    private val viewModel = SignInViewModel(Repositories.firebaseAuthRepository)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)

        binding.noAccountClickableText.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        binding.signInButton.setOnClickListener {
            val email = binding.signUpEmailInput.text.toString()
            val password = binding.signUpPasswordInput.text.toString()

            if (email.isBlank() || password.isBlank()) Toast.makeText(activity, "Заполните все поля", Toast.LENGTH_SHORT).show()
            else {
                viewModel.signIn(email, password)
                updatePending()
            }
        }


        viewModel.operationState.observe(viewLifecycleOwner) {
            when (it) {
                is OperationState.Success -> findNavController().navigate(R.id.action_signInFragment_to_tabsFragment)
                is OperationState.Error -> {
                    updateReady()
                    Toast.makeText(activity, "Произошла ошибка, попробуйте снова", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root;
    }

    override fun onStart() {
        super.onStart()
        if (viewModel.isSignedIn()) {
            findNavController().navigate(R.id.action_signInFragment_to_tabsFragment)
        }

    }

    private fun updatePending() {
        binding.root.children.forEach {
            if (it.id == R.id.signInProgressBar) it.visibility = View.VISIBLE
            else it.visibility = View.GONE
        }
    }

    private fun updateReady() {
        binding.root.children.forEach {
            if (it.id == R.id.signInProgressBar) it.visibility = View.GONE
            else it.visibility = View.VISIBLE
        }
    }
}