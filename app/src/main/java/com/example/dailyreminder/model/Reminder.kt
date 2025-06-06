package com.example.dailyreminder.model

import com.google.gson.annotations.SerializedName

data class Reminder(
    val id: Int,
    val title: String,
    val date: String,
    val description: String?,
    @SerializedName("reminder_time")
    val reminderTime: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)

