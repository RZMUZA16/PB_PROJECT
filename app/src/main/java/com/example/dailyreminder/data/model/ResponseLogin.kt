package com.example.dailyreminder.data.model

import com.google.gson.annotations.SerializedName

data class ResponseLogin(

	@field:SerializedName("access_token")
	val accessToken: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("token_type")
	val tokenType: String,

	@field:SerializedName("user")
	val user: User
)


