package com.example.dailyreminder.data.model

data class LoginResponse(
    val token: String,
    val user: UserDto
)
