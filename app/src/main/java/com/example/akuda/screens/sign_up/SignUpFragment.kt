package com.example.akuda.screens.sign_up

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
import com.example.akuda.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding

    private val viewModel = SignUpViewModel(Repositories.firebaseAuthRepository)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        binding.signUpGoBackButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.signUpButton.setOnClickListener {
            val email = binding.signUpEmailInput.text.toString()
            val password = binding.signUpPasswordInput.text.toString()
            val nickname = binding.signUpNameInput.text.toString()
            if (email.isBlank() || password.isBlank() || nickname.isBlank()) {
                Toast.makeText(activity, "Заполните все поля", Toast.LENGTH_SHORT).show();
            } else {
                viewModel.signUp(email, password, nickname)
                updatePending()
            }
        }

        viewModel.operationState.observe(viewLifecycleOwner) {
            when (it) {
                is OperationState.Success -> findNavController().navigate(R.id.action_signUpFragment_to_tabsFragment)
                is OperationState.Error -> {
                    updateReady()
                    Toast.makeText(activity, "Произошла ошибка, попробуйте снова", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root;
    }

    private fun updatePending() {
        binding.root.children.forEach {
            if (it.id == R.id.signUpProgressBar) it.visibility = View.VISIBLE
            else it.visibility = View.GONE
        }
    }

    private fun updateReady() {
        binding.root.children.forEach {
            if (it.id == R.id.signUpProgressBar) it.visibility = View.GONE
            else it.visibility = View.VISIBLE
        }
    }

}