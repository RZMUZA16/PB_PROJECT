package com.example.dailyreminder.data.repository

import com.example.dailyreminder.data.api.RetrofitInstance
import com.example.dailyreminder.data.model.UserDto
import com.example.dailyreminder.data.model.LoginResponse
import retrofit2.Response

class AuthRepository {

    suspend fun login(email: String, password: String): Response<LoginResponse> {
        val body = mapOf(
            "email" to email,
            "password" to password
        )
        return RetrofitInstance.authApiService.login(body)
    }

    suspend fun register(user: UserDto): Response<Void> {
        return RetrofitInstance.authApiService.register(user)
    }
}
