package com.example.dailyreminder.data.repository

import com.example.dailyreminder.data.api.RetrofitInstance
import com.example.dailyreminder.data.model.ResponseLogin
import com.example.dailyreminder.data.model.ResponseRegister
import com.example.dailyreminder.data.model.UserDto
import retrofit2.Response

class AuthRepository {

    suspend fun login(email: String, password: String): Response<ResponseLogin> {
        val body = mapOf(
            "email" to email,
            "password" to password
        )
        return RetrofitInstance.authApiService.login(body)
    }

    suspend fun register(user: UserDto): Response<ResponseRegister> {
        return RetrofitInstance.authApiService.register(user)
    }
}
