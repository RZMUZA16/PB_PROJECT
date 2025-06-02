package com.example.dailyreminder.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyreminder.data.model.UserDto
import com.example.dailyreminder.data.repository.AuthRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import java.sql.ResultSet

class AuthViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    private val _loginResult = MutableLiveData<Result<String>>()
    val loginResult: LiveData<Result<String>> = _loginResult

    private val _registerResult = MutableLiveData<Result<String>>()
    val registerResult: LiveData<Result<String>> = _registerResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = authRepository.login(email, password)
                if (response.isSuccessful) {
                    val token = response.body()?.token
                    if (!token.isNullOrEmpty()) {
                        _loginResult.postValue(Result.success(token))
                    } else {
                        _loginResult.postValue(Result.failure(Exception("No Token")))
                    }
                } else {
                    _loginResult.postValue(Result.failure(Exception("LOGIN GAGAL: ${response.message()}")))
                }
            } catch (e: Exception) {
                _loginResult.postValue(Result.failure(e))
            }
        }
    }

    fun register(userDto: UserDto) {
        viewModelScope.launch {
            try {
                val response = authRepository.register(userDto)
                if (response.isSuccessful) {
                    _registerResult.postValue(Result.success("Register berhasil"))
                } else {
                    _registerResult.postValue(Result.failure(Exception("Gagal: ${response.message()}")))
                }
            } catch (e: Exception) {
                _registerResult.postValue(Result.failure(e))
            }
        }
    }

}