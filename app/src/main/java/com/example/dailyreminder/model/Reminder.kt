package com.example.dailyreminder.model

data class Reminder(
    val id: Int? = null,
    val title: String,
    val description: String,
    val date: String,
    val time: String,
    val isCompleted: Boolean,
    val createdAt: String? = null,
    val updatedAt: String? = null
)
