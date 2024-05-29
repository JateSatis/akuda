package com.example.akuda.screens.sign_up

import android.provider.ContactsContract.CommonDataKinds.Nickname
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.akuda.model.auth.FirebaseAuthRepository
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.launch

class SignUpViewModel(private val authRepository: FirebaseAuthRepository) : ViewModel() {

    private val _operationState  = MutableLiveData<OperationState>()
    val operationState: LiveData<OperationState>
        get() = _operationState

    fun signUp(email: String, password: String, nickname: String) {
        viewModelScope.launch {
            try {
                val result = authRepository.createUserWithEmailAndPassword(email, password, nickname)
                _operationState.postValue(OperationState.Success(result))
            } catch (e: Exception) {
                _operationState.postValue(OperationState.Error(e))
            }
        }
    }
}

sealed class OperationState {
    data class Success(val result: AuthResult) : OperationState()
    data class Error(val exception: Throwable) : OperationState()
}