package com.example.dailyreminder.data.model

import com.google.gson.annotations.SerializedName

data class ResponseRegister(

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("user")
    val user: User,

    @field:SerializedName("access_token")
    val accessToken: String,

    @field:SerializedName("token_type")
    val tokenType: String
)
