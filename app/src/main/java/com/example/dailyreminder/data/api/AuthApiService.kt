package com.example.dailyreminder.data.api

import com.example.dailyreminder.data.model.ResponseLogin
import com.example.dailyreminder.data.model.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("api/login")
    suspend fun login(
        @Body credentials: Map<String, String>
    ): Response<ResponseLogin>

    @POST("api/register")
    suspend fun register(
        @Body user: UserDto
    ): Response<Void>
}
