package com.example.dailyreminder.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dailyreminder.utils.SessionManager

class AccountViewModel(private val sessionManager: SessionManager) : ViewModel() {

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String> = _userEmail

    fun loadUserData() {
        _userName.value = sessionManager.getUserName()
        _userEmail.value = sessionManager.getUserEmail()
    }

    fun logout() {
        sessionManager.clearSession()
    }
}
