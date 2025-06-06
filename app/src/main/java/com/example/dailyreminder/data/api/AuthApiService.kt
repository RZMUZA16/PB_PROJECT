package com.example.dailyreminder.data.api

import com.example.dailyreminder.data.model.LoginResponse
import com.example.dailyreminder.data.model.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("login")
    suspend fun login(
        @Body credentials: Map<String, String>
    ): Response<LoginResponse>

    @POST("register")
    suspend fun register(
        @Body user: UserDto
    ): Response<Void>
}
