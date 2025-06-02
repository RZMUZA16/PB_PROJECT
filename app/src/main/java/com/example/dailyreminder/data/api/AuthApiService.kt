package com.example.dailyreminder.data.api

interface AuthApiService {
    @POST("login")
    suspend fun login(@Body body: Map<String, String>): Response<LoginResponse>
}
