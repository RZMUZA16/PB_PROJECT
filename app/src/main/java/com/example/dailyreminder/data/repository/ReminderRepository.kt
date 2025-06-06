package com.example.dailyreminder.data.repository

import ReminderDto
import com.example.dailyreminder.data.api.ReminderApiService

import retrofit2.Response

class ReminderRepository(private val api: ReminderApiService) {

    suspend fun getReminders(token: String): Response<List<ReminderDto>> {
        return api.getReminders("Bearer $token")
    }

    suspend fun getReminderById(token: String, id: Int): Response<ReminderDto> {
        return api.getReminderById("Bearer $token", id)
    }

    suspend fun createReminder(token: String, reminder: ReminderDto): Response<ReminderDto> {
        return api.createReminder("Bearer $token", reminder)
    }

    suspend fun updateReminder(token: String, id: Int, reminder: ReminderDto): Response<ReminderDto> {
        return api.updateReminder("Bearer $token", id, reminder)
    }

    suspend fun deleteReminder(token: String, id: Int): Response<Void> {
        return api.deleteReminder("Bearer $token", id)
    }
}
