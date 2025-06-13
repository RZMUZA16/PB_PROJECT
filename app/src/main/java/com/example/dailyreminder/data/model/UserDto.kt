package com.example.dailyreminder.data.model

import com.google.gson.annotations.SerializedName

data class UserDto(
    val name: String,
    val email: String,
    val password: String,
    @field:SerializedName("password_confirmation")
    val passwordConfirm: String
)
