package com.example.akuda.screens.sign_in

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.akuda.model.auth.FirebaseAuthRepository
import com.example.akuda.screens.sign_up.OperationState
import kotlinx.coroutines.launch

class SignInViewModel(private val authRepository: FirebaseAuthRepository) : ViewModel() {

    private val _operationState  = MutableLiveData<OperationState>()
    val operationState: LiveData<OperationState>
        get() = _operationState

    fun isSignedIn() = authRepository.isSignedIn()


    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            try {
                val result = authRepository.signInWithEmailAndPassword(email, password)
                _operationState.postValue(OperationState.Success(result))
            } catch (e: Exception) {
                _operationState.postValue(OperationState.Error(e))
            }
        }
    }
}