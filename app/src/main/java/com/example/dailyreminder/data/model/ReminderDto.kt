package com.example.dailyreminder.data.model


import com.google.gson.annotations.SerializedName

data class ReminderDto(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("date") val date: String,
    @SerializedName("time") val time: String,
    @SerializedName("is_completed") val isCompleted: Boolean
)

