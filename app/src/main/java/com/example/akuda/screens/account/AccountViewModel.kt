package com.example.akuda.screens.account

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.akuda.model.account.Account
import com.example.akuda.model.account.FirebaseAccountRepository
import kotlinx.coroutines.launch

class AccountViewModel(private val accountRepository: FirebaseAccountRepository) : ViewModel() {

    private val _account: MutableLiveData<Account?> = MutableLiveData()
    val account: MutableLiveData<Account?>
        get() = _account

    private val _photoUpdateSuccess = MutableLiveData<Boolean>()
    val photoUpdateSuccess: LiveData<Boolean>
        get() = _photoUpdateSuccess

    private val _nicknameUpdateSuccess = MutableLiveData<Boolean>()
    val nicknameUpdateSuccess: LiveData<Boolean>
        get() = _nicknameUpdateSuccess

    fun fetchAccountInfo() {
        viewModelScope.launch {
            val account = accountRepository.fetchAccountInfo()
            _account.postValue(account)
        }
    }

    fun updateAccountPhoto(photo: Uri) {
        viewModelScope.launch {
            val result = accountRepository.saveAccountPhoto(photo.toString())
            if (result) {
                _photoUpdateSuccess.postValue(true)
            } else {
                _photoUpdateSuccess.postValue(false)
            }
        }
    }

    fun updateAccountNickname(nickname: String) {
        viewModelScope.launch {
            val result = accountRepository.saveAccountNickname(nickname)
            if (result) {
                _photoUpdateSuccess.postValue(true)
            } else {
                _photoUpdateSuccess.postValue(false)
            }
        }
    }
}